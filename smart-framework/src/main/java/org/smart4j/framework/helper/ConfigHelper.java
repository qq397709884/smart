package org.smart4j.framework.helper;


import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.utils.PropsUtils;

import java.util.Properties;

/**
 * Created by asus on 2017/10/23.
 */
public class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtils.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackage() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view");
    }

    public static String getAppAssetPath() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }
}
