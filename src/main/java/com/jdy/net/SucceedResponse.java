package com.jdy.net;

import java.util.Map;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 10:52
 */
public class SucceedResponse extends AbstractResponse {

    public SucceedResponse(String format, Object... parameters) {
        super(true, 200, String.format(format, parameters));
    }

    public SucceedResponse(Map<String, Object> dataMap){
        this(200, dataMap);
    }

    public SucceedResponse(int code, Map<String, Object> dataMap) {
        this(true, code, dataMap);
    }

    public SucceedResponse(boolean status, int code, Map<String, Object> dataMap) {
        super(status, code, dataMap);
    }
}
