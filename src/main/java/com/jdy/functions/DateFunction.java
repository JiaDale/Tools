package com.jdy.functions;

import com.jdy.util.DateUtil;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/18 14:29
 */
public class DateFunction implements Function<Object, Date> {

    private DateFunction() {
    }

    public static Date convert(Object value) {
        return FunctionHolder.singleton.apply(value);
    }

    @Override
    public Date apply(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        if (value instanceof String) {
            return DateUtil.convert(value.toString());
        }

        if (value instanceof Long) {
            new Date((Long) value);
        }

        throw new IllegalArgumentException("无法预知 value 的数据类型！");
    }

    private static class FunctionHolder {
        private static final DateFunction singleton = new DateFunction();
    }

    public static DateFunction getInstance() {
        return FunctionHolder.singleton;
    }
}
