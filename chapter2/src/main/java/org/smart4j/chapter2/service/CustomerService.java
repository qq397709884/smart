package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.utils.PropsUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by asus on 2017/10/22.
 */
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties properties = PropsUtils.loadProps("config.properties");
        DRIVER = properties.getProperty("jdbc.driver");
        URL = properties.getProperty("jdbc.url");
        USERNAME = properties.getProperty("jdbc.username");
        PASSWORD = properties.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

//    public List<Customer> getCustomerList(String keyWord) {
//        Connection coon = null;
//        try {
//            List<Customer> customerList = new ArrayList<>();
//            String sql = "SELECT * FROM customer";
//            coon = DatabaseHelper.getConnection();
//            PreparedStatement statement = coon.prepareStatement(sql);
//            ResultSet res = statement.executeQuery();
//            while (res.next()) {
//                Customer customer = new Customer();
//                customer.setId(res.getLong("id"));
//                customer.setName(res.getString("name"));
//                customer.setContact(res.getString("contact"));
//                customer.setTelephone(res.getString("telephone"));
//                customer.setEmail(res.getString("email"));
//                customer.setRemark(res.getString("remark"));
//                customerList.add(customer);
//            }
//            return customerList;
//        } catch (Exception e) {
//            LOGGER.error("execute sql failure ", e);
//        } finally {
//            DatabaseHelper.closeConnection();
//        }
//        return null;
//    }

    public List<Customer> getCustomerList() {
        try {
            Connection coon = DatabaseHelper.getConnection();
            String sql = "SELECT * FROM customer";
            return DatabaseHelper.queryEntityList(Customer.class, coon, sql, null);
        } catch (Exception e) {
            LOGGER.error("query error", e);
        }
        return null;
    }

    public Customer getCustomer(long customerId) {
        String SQL = "select * from customer where id = ?";
        return DatabaseHelper.queryEntity(Customer.class, SQL, customerId);
    }

    public boolean createCustomer(Map<String, Object> filedMap) {
        return DatabaseHelper.insertEntity(Customer.class, filedMap);
    }


    public boolean updateCustomer(long customerId, Map<String, Object> filedMap) {
        return DatabaseHelper.updateEntity(Customer.class, customerId, filedMap);
    }


    public boolean deleteCustomer(long customerId) {
        return DatabaseHelper.deleteEntity(Customer.class, customerId);
    }
}
