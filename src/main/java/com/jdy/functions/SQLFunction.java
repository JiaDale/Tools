package com.jdy.functions;

import com.jdy.io.ConfigureUtils;
import com.jdy.util.DateUtil;

import java.util.Date;
import java.util.function.Function;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/18 13:27
 */
public class SQLFunction implements Function<Object, String> {

    private SQLFunction() {
    }

    @Override
    public String apply(Object value) {
        if (value instanceof String) {
            return "'" + value.toString() + "'";
        }

        if (value instanceof Number) {
            return String.valueOf(value);
        }

        if (value instanceof Date) {
            if (ConfigureUtils.isMySQL()) {
                return "'" + DateUtil.dateFormat(value, DateUtil.HIGH_PRECISION_FORMAT) + "'";
            }
            return DateUtil.dateFormat(value, DateUtil.HIGH_PRECISION_FORMAT);
        }

        return value.toString();
    }

    private static class SQLFunctionHolder {
        private static final SQLFunction singleton = new SQLFunction();
    }


    public static SQLFunction getInstance() {
        return SQLFunctionHolder.singleton;
    }

}
