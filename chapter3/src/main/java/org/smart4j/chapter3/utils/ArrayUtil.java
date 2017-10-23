package org.smart4j.chapter3.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by asus on 2017/10/24.
 */
public class ArrayUtil {

    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
