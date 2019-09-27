package com.jdy.entity;

import com.jdy.functions.BooleanFunction;
import com.jdy.functions.StringFunction;
import com.jdy.util.CheckUtil;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/18 0:34
 */
public interface Getter<T> {

    Object get(T key);

    default <V> V get(T key, final V defaultValue) {
        return CheckUtil.checkValue(get(key), defaultValue, value -> {
            if (defaultValue.getClass().isAssignableFrom(value.getClass())) {
                return (V) value;
            }

            if (defaultValue instanceof String) {
                return (V) StringFunction.convert(value);
            }

            if (defaultValue instanceof Boolean) {
                return (V) BooleanFunction.convert(value);
            }

            throw new UnsupportedOperationException(String.format("抱歉无法将%s数据转换成%s数据", value.getClass().getName(), defaultValue, getClass().getName()));
        });
    }
}
