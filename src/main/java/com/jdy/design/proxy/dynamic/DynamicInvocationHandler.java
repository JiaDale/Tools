package com.jdy.design.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * {describe}
 *
 * @Author : Dale
 * @Create on 2019/1/3 17:49
 * @Version 1.0
 */
public class DynamicInvocationHandler implements InvocationHandler {
    private Object object;

    public DynamicInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("这里些代理对象方法执行前的逻辑...");

        Object invoke = method.invoke(object, args);

        System.out.println("这里些代理对象方法执行后的逻辑...");

        return invoke;
    }
}
