package com.jdy.net;


/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 10:22
 */
public class FailureResponse extends AbstractResponse {

    public FailureResponse(String format, Object... parameters) {
        this(404, String.format(format, parameters));
    }

    public FailureResponse(int code, String format, Object... parameters) {
        super(false, code, String.format(format, parameters));
    }

}
