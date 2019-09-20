package com.jdy.entity;

import com.jdy.functions.BooleanFunction;
import com.jdy.functions.StringFunction;

import java.util.Map;

public class Response extends AbstractEntity {
    private static final long serialVersionUID = -3713543329352166665L;

    public final String STATUS = "status";
    public final String CODE = "code";
    public final String MSG = "msg";
    public final String DATA = "Data";

    public Response(boolean status, String text) {
        this(status, 200, text);
    }

    public Response(boolean status, int code, String text) {
        set(STATUS, status);
        set(CODE, code);
        set(MSG, text);
    }

    public Response(boolean status, Map<String, Object> data) {
        set(STATUS, status);
        set(CODE, 200);
        set(DATA, data);
    }

    public boolean isSucceed() {
        return BooleanFunction.getInstance().apply(get(STATUS));
    }

    public int getCode() {
        Object object = get(CODE);
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        if (isSucceed()) {
            return 200;
        }
        throw new IllegalArgumentException("响应参数存在问题！");
    }

    public String getMessage() {
        return StringFunction.getInstance().apply(get(MSG));
    }

    @Override
    public String toString() {
        //向前台传一个json串
        return super.toString();
    }
}
