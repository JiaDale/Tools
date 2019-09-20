package com.jdy.design.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * {describe}
 *
 * @Author : Dale
 * @Create on 2019/1/3 18:04
 * @Version 1.0
 */
public class ProxyFactory {

    public static <T> Subject getSubject(Class<T> clz) {
        InvocationHandler invocationHandler = null;
        try {
            invocationHandler = new DynamicInvocationHandler(clz.getConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
        return (Subject) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), invocationHandler);
    }
}
