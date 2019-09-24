package com.jdy.database;




import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/24 1:33
 */
public class ProxyDAOFactory {

    public static <T> Service create(Class<T> clz) {
        InvocationHandler invocationHandler;
        try {
            invocationHandler = new DataAccessObjectInvocationHandler(clz.getConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
        return (Service) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), invocationHandler);
    }

    private static class DataAccessObjectInvocationHandler implements InvocationHandler{
        private Object object;
        public  DataAccessObjectInvocationHandler(Object newInstance) {
            object = newInstance;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            /**
             *  提前做一些拦截, 这里留一个文职
             */
            return method.invoke(object, args);
        }
    }
}
