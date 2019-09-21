package com.jdy.io;

import com.jdy.functions.BooleanFunction;

import java.util.Objects;
import java.util.Properties;

/**
 * Description: Tools
 * Created by Dale on 2019/9/21 11:55
 */
public class PropertiesUtil {

    private static final Properties systemCache = System.getProperties();

    public static boolean isTrue(String cacheKey, boolean isWeb) {
        return BooleanFunction.getInstance().apply(get(cacheKey, isWeb));
    }

    public static Object get(String cacheKey, Object defaultValue) {
        Object value = systemCache.get(cacheKey);
        if (Objects.isNull(value)) {//如果没有取到，会将此值存储到缓存中，下次可以直接取值
            systemCache.put(cacheKey, defaultValue);
            return defaultValue;
        }
        return value;
    }
}
