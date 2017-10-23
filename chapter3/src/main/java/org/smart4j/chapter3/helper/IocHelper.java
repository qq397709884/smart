package org.smart4j.chapter3.helper;

import org.smart4j.chapter3.annotation.Inject;
import org.smart4j.chapter3.utils.ArrayUtil;
import org.smart4j.chapter3.utils.CollectionUtil;
import org.smart4j.chapter3.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by asus on 2017/10/23.
 */
public final class IocHelper {

    static {
        //获取所有的Bean 类与 Bean 实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmptyMap(beanMap)) {
            //遍历Bean map
            for (Map.Entry<Class<?>, Object> beanEntity : beanMap.entrySet()) {
                // 类
                Class<?> beanClass = beanEntity.getKey();
                //当前类实例
                Object objInstance = beanEntity.getValue();
                //当前类的字段
                Field[] beanFileds = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFileds)) {
                    //遍历字段
                    for (Field beanField : beanFileds) {
                        //字段的注解有 Inject
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 得到字段的类型
                            Class<?> beanFieldClass = beanField.getType();
                            //字段实例有注册到bean 容器中
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //设置字段的实例
                                ReflectionUtil.setFiled(objInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }

    }
}
