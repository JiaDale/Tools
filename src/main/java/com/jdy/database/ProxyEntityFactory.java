package com.jdy.database;

import com.jdy.util.ClassUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/27 12:33
 */
public class ProxyEntityFactory {


    public static <T> T create(Class<T> tClass, Map<String, Object> map) {
//        Constructor<?> constructor = ClassUtil.getConstructor(tClass.getConstructors(), Map.class);
//        if (null == constructor) {
//            return null;
//        }

        InvocationHandler invocationHandler = null;
        try {
//            invocationHandler = new EntityInvocationHandler(tClass.getConstructors(map.getClass()).newInstance(map));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return create(tClass.getInterfaces(), invocationHandler);
    }

    @SuppressWarnings("unchecked")
    private static <T> T create(Class<?>[] interfaces, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(ClassUtil.getClassLoader(), interfaces, invocationHandler);
    }

    private static class EntityInvocationHandler implements InvocationHandler {

        private Object instance;

        EntityInvocationHandler(Object t) {
            instance = t;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(instance, args);
        }
    }

}
