package com.jdy.check;


import com.jdy.functions.StringFunction;

/**
 * Description: Tools
 * Created by Administrator on 2019/6/21 20:21
 */
public class DefaultCheckable implements Checkable<Object> {
    @SuppressWarnings("unchecked")
    @Override
    public <R> R check(Object value, boolean retDefault, R defaultValue) {
        if (retDefault) { //如果判断条件为true，返回默认值
            return defaultValue;
        }

        //返回数据类型为String
        if (defaultValue instanceof String) {
            return (R) StringFunction.getInstance().apply(value);
        }

        throw new UnsupportedOperationException(String.format("暂时不支持将%s数据类型转换成%s数据类型", value.getClass().getSimpleName(), defaultValue.getClass().getSimpleName()));
    }
}
