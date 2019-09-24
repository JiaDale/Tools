package com.jdy.net;

import com.google.gson.Gson;
import com.jdy.entity.Setter;
import com.jdy.functions.BooleanFunction;
import com.jdy.functions.StringFunction;
import com.jdy.util.CheckUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 10:32
 */
class AbstractResponse implements Response {

    private Map<String, Object> dataMap = new ConcurrentHashMap<>();

    AbstractResponse(boolean status, int code, String msg) {
        this(status, code, msg, new HashMap<>());
    }

    AbstractResponse(boolean status, int code, Map<String, Object> dataMap) {
        this(status, code, "", dataMap);
    }

    AbstractResponse(boolean status, int code, String msg, Map<String, Object> dataMap) {
        set(STATUS, status);
        set(CODE, code);
        set(MSG, msg);
        set(DATA, dataMap);
    }

    @Override
    public boolean isSucceed() {
        return BooleanFunction.getInstance().apply(get(STATUS));
    }

    @Override
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

    @Override
    public String getMessage() {
        return StringFunction.getInstance().apply(get(MSG));
    }

    @Override
    public Map<String, Object> getDataMap() {
        return dataMap;
    }


    @Override
    public String toString() {
        //向前台传一个json串
        return new Gson().toJson(getDataMap());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V get(String key) {
        return (V) CheckUtil.checkValue(dataMap.get(key), null);
    }

    @Override
    public <V> Setter set(String key, V value, boolean ignoreCase) {
        dataMap.put(key, value);
        return this;
    }
}
