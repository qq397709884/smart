package org.smart4j.framework.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by asus on 2017/10/22.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmptyMap(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

    public static boolean isNotEmptyMap(Map<?, ?> map) {
        return !isEmptyMap(map);
    }
}

