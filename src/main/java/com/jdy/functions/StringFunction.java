package com.jdy.functions;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/18 13:27
 */
public class StringFunction implements Function<Object, String> {

    private StringFunction() {
    }

    private static class FunctionHolder {
        private static final StringFunction singleton = new StringFunction();
    }


    public static StringFunction getInstance() {
        return FunctionHolder.singleton;
    }


    @Override
    public String apply(Object value) {
        if (Objects.isNull(value)){
            return  "Null";
        }

        if (value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long) {
            return String.format(Locale.CHINA, "%d", value);
        }

        if (value instanceof Float || value instanceof Double) {
            return String.format(Locale.CHINA, "%f", value);
        }

        if (value instanceof Date) {
            return String.format(Locale.CHINA, "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS:%1$tL", value);
        }

        return value.toString();
    }

}
