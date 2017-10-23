package org.smart4j.chapter3.utils;


import com.sun.deploy.util.StringUtils;

/**
 * Created by asus on 2017/10/22.
 */
public class CastUtil {

    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    public static String castString(Object obj, String defaultString) {
        return obj != null ? String.valueOf(obj) : defaultString;
    }

    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    public static double castDouble(Object obj, double defaultValues) {
        double doubleValues = defaultValues;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValues = Double.parseDouble(strValue);
                } catch (Exception e) {

                }
            }
        }
        return doubleValues;
    }

    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    public static long castLong(Object obj, long defaultValues) {
        long longValues = defaultValues;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    longValues = Long.parseLong(strValue);
                } catch (Exception e) {

                }
            }
        }
        return longValues;
    }

    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    public static int castInt(Object obj, int defaultValues) {
        int intValues = defaultValues;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValues = Integer.parseInt(strValue);
                } catch (Exception e) {

                }
            }
        }
        return intValues;
    }

    public static boolean castBoolen(Object obj) {
        return CastUtil.castBoolen(obj, false);
    }

    public static boolean castBoolen(Object obj, boolean defaultValue) {
        boolean boolenValue = defaultValue;
        if (obj != null) {
            boolenValue = Boolean.parseBoolean(castString(obj));
        }

        return boolenValue;
    }

}

