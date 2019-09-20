package com.jdy.functions;

import com.jdy.util.ArrayUtil;

import java.util.Objects;
import java.util.function.Function;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/18 14:29
 */
public class BooleanFunction implements Function<Object, Boolean> {

    /**
     * 表示为真的字符串
     */
    private static final String[] TRUE_ARRAY = {"true", "yes", "y", "t", "ok", "1", "on", "是", "对", "真"};

    private BooleanFunction() {
    }

    @Override
    public Boolean apply(Object value) {
        if (Objects.isNull(value)) {
            return false;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        }


        if (value instanceof String) {
            return ArrayUtil.hasElement(TRUE_ARRAY, value.toString()::equalsIgnoreCase);
        }

        if (value instanceof Number) {
            return ((Number) value).longValue() > 0;
        }

        return false;
    }

    private static class FunctionHolder {
        private static final BooleanFunction singleton = new BooleanFunction();
    }

    public static BooleanFunction getInstance() {
        return FunctionHolder.singleton;
    }
}
