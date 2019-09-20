package com.jdy.reflection;

import java.util.Objects;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/17 18:01
 */
public class DefaultCaller implements Caller {

    private StackTraceElement callerStackTrace;

    public DefaultCaller(StackTraceElement element) {
        if (Objects.isNull(element))
            throw new NullPointerException("调用者的类栈信息未获取到！");
        callerStackTrace = element;
    }

    @Override
    public StackTraceElement getCallerStackTrace() {
        return callerStackTrace;
    }
}
