package com.jdy.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/14 17:02
 */
public class ClassUtil {
    /**
     * 是否为静态方法
     *
     * @param method 方法
     * @return 是否为静态方法
     */
    public static boolean isStatic(Method method) {
        Objects.requireNonNull(method, "Method to provided is null.");
        return Modifier.isStatic(method.getModifiers());
    }

    /**
     * 获得对象数组的类数组
     *
     * @param args 对象数组，如果数组中存在{@code null}元素，则此元素被认为是Object类型
     * @return 类数组
     */
    public static Class<?>[] getClasses(Object... args) {
        if (Objects.isNull(args)) {
            return null;
        }

        Class<?>[] classes = new Class<?>[args.length];
        Object obj;

        for (int i = 0; i < args.length; i++) {
            obj = args[i];
            classes[i] = (null == obj) ? Object.class : obj.getClass();
        }
        return classes;
    }


    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(T obj) {
        return ((null == obj) ? null : (Class<T>) obj.getClass());
    }


    public static Class<?> forName(String className) throws ClassNotFoundException {
        Class clazz = null;
        try {
            clazz = getClassLoader().loadClass(className);
        } catch (Exception e) {
        }

        if (clazz == null) {
            clazz = Class.forName(className);
        }

        return clazz;
    }


    /**
     * 获取{@link ClassLoader}<br>
     * 获取顺序如下：<br>
     *
     * <pre>
     * 1、获取当前线程的ContextClassLoader
     * 2、获取{@link ClassUtil}类对应的ClassLoader
     * 3、获取系统ClassLoader（{@link ClassLoader#getSystemClassLoader()}）
     * </pre>
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassUtil.class.getClassLoader();
            if (null == classLoader) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

    /**
     * 获取当前线程的{@link ClassLoader}
     *
     * @return 当前线程的class loader
     * @see Thread#getContextClassLoader()
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
