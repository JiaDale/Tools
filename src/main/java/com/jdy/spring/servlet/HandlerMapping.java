package com.jdy.spring.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 17:03
 */
public class HandlerMapping {

    private final Object controller;	//保存方法对应的实例
    private Method method;		//保存映射的方法
    private final Pattern pattern;    //URL的正则匹配


    public HandlerMapping(Pattern pattern, Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public boolean match(String url) {
        return pattern.matcher(url).matches();
    }
}
