package org.smart4j.chapter2.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.utils.CollectionUtil;
import org.smart4j.chapter2.utils.PropsUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by asus on 2017/10/22.
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL;

    private static final BasicDataSource DATA_SOURCE;

    static {
        CONNECTION_THREAD_LOCAL = new ThreadLocal<>();


        Properties properties = PropsUtils.loadProps("config.properties");
        DRIVER = properties.getProperty("jdbc.driver");
        URL = properties.getProperty("jdbc.url");
        USERNAME = properties.getProperty("jdbc.username");
        PASSWORD = properties.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    public static Connection getConnection() {
        Connection coon = CONNECTION_THREAD_LOCAL.get();
        if (coon == null) {
            try {
                coon = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection faiure", e);
                e.printStackTrace();
            } finally {
                CONNECTION_THREAD_LOCAL.set(coon);
            }
        }
        return coon;
    }


//    public static void closeConnection(Connection coon) {
//        if (coon != null) {
//            try {
//                coon.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    public static <T> List<T> queryEntityList(Class<T> entityClass, Connection coon, String sql, Object... params) {
        List<T> entityList;
        try {
            entityList = QUERY_RUNNER.query(coon, sql, new BeanListHandler<T>(entityClass), params);
        } catch (Exception e) {
            LOGGER.error("query entity list failue ", e);
            throw new RuntimeException();
        }
        return entityList;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity = null;
        Connection connection = getConnection();
        try {
            entity = QUERY_RUNNER.query(connection, sql, new BeanHandler<T>(entityClass), params);
            LOGGER.info("查询sql：{}", sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public static List<Map<String, Object>> exectuQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        Connection connection = getConnection();
        try {
            result = QUERY_RUNNER.query(connection, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        Connection connection = getConnection();
        try {
            rows = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> filedMap) {
        if (CollectionUtil.isEmptyMap(filedMap)) {
            LOGGER.error("can not insert entity: fieldMap is empty");
            return false;
        }
        String sql = "insert into " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : filedMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;
        Object[] objects = filedMap.values().toArray();
        LOGGER.info("执行新增sql:{}", sql);
        return executeUpdate(sql, objects) == 1;

    }

    public static <T> boolean updateEntity(Class<T> entity, long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmptyMap(fieldMap)) {
            LOGGER.error("can not update entity fieldMap is empty");
            return false;
        }
        String sql = "update " + getTableName(entity) + " set ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " where id=?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        LOGGER.info("执行更新sql:{}", sql);
        return executeUpdate(sql, params) == 1;
    }

    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "delete from " + getTableName(entityClass) + " where id=?";
        LOGGER.info("执行删除sql:{}", sql);
        return executeUpdate(sql, id) == 1;
    }


    private static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }


}
