package com.jdy.reflection;

import com.jdy.annotation.AnnotateUtils;
import com.jdy.annotation.CallerSensitive;
import com.jdy.cache.SimpleCache;
import com.jdy.util.ArrayUtil;
import com.jdy.util.ClassUtil;
import com.jdy.util.TextUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/14 14:41
 */
public class ReflectUtils {

    /**
     * 字段缓存
     */
    private static final SimpleCache<Class<?>, Field[]> FIELDS_CACHE = new SimpleCache<>();
    /**
     * 方法缓存
     */
    private static final SimpleCache<Class<?>, Method[]> METHODS_CACHE = new SimpleCache<>();

    /**
     * 调用方法缓存
     */
    private static final SimpleCache<Class<?>, String> CALLER_METHODS_CACHE = new SimpleCache<>();

    /**
     * 获得一个类中所有字段列表，包括其父类中的字段
     *
     * @param clazz 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getFields(Class<?> clazz) throws SecurityException {
        Field[] allFields = FIELDS_CACHE.get(clazz);
        if (Objects.isNull(clazz)) {
            return FIELDS_CACHE.put(clazz, getFields(clazz, true));
        }
        return allFields;
    }

    private static Field[] getFields(Class<?> clazz, boolean withSuperClassFields) {
        Objects.requireNonNull(clazz, "[Assertion failed] - this argument is required; it must not be null");

        Field[] allFields = null;
        Class<?> searchType = clazz;
        Field[] declaredFields;

        while (searchType != null) {
            declaredFields = searchType.getDeclaredFields();
            if (null == allFields) {
                allFields = declaredFields;
            } else {
                allFields = ArrayUtil.append(allFields, declaredFields);
            }
            searchType = withSuperClassFields ? searchType.getSuperclass() : null;
        }

        return allFields;
    }

    public static Method getMethod(Object object, String methodName, Object... args) {
        if (Objects.isNull(object) || Objects.isNull(methodName)) {
            return null;
        }
        return getMethod(object.getClass(), methodName, getClasses(args));
    }


    /**
     * 查找指定方法 如果找不到对应的方法则返回<code>null</code>
     *
     * <p>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回<code>null</code>。
     * </p>
     *
     * @param clazz      类，如果为{@code null}返回{@code null}
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        return getMethod(clazz, false, methodName, paramTypes);
    }

    /**
     * 查找指定方法 如果找不到对应的方法则返回<code>null</code>
     *
     * <p>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回<code>null</code>。
     * </p>
     *
     * @param clazz      类，如果为{@code null}返回{@code null}
     * @param ignoreCase 是否忽略大小写
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getMethod(Class<?> clazz, boolean ignoreCase, String methodName, Class<?>... paramTypes) {
        if (Objects.isNull(clazz) || TextUtils.isEmpty(methodName)) {
            return null;
        }

        final Method[] methods = getMethods(clazz);
        if (ArrayUtil.isEmpty(methods)) {
            return null;
        }

        for (Method method : methods) {
            if (TextUtils.equals(methodName, method.getName(), ignoreCase)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 获得一个类中所有方法列表，包括其父类中的方法
     *
     * @param clazz 类
     * @return 方法列表
     * @throws SecurityException 安全检查异常
     */
    public static Method[] getMethods(Class<?> clazz) {
        Method[] allMethods = METHODS_CACHE.get(clazz);
        if (Objects.isNull(allMethods)) {
            return METHODS_CACHE.put(clazz, getMethods(clazz, true));
        }
        return allMethods;
    }

    /**
     * 获得一个类中所有方法列表，直接反射获取，无缓存
     *
     * @param clazz                 类
     * @param withSuperClassMethods 是否包括父类的方法列表
     * @return 方法列表
     */
    private static Method[] getMethods(Class<?> clazz, boolean withSuperClassMethods) {
        Objects.requireNonNull(clazz, "[Assertion failed] - this argument is required; it must not be null");

        Method[] allMethods = null;
        Class<?> searchType = clazz;
        Method[] declaredMethods;
        while (searchType != null) {
            declaredMethods = searchType.getDeclaredMethods();
            if (Objects.isNull(allMethods)) {
                allMethods = declaredMethods;
            } else {
                allMethods = ArrayUtil.append(allMethods, declaredMethods);
            }
            searchType = withSuperClassMethods ? searchType.getSuperclass() : null;
        }
        return allMethods;
    }

    /**
     * 获得指定类过滤后的Public方法列表
     *
     * @param clazz           查找方法的类
     * @param methodPredicate 过滤器
     * @return 过滤后的方法列表
     */
    public static Method[] getMethods(Class<?> clazz, Predicate<Method> methodPredicate) {
        if (Objects.isNull(clazz)) {
            return null;
        }
        return ArrayUtil.filter(getMethods(clazz), methodPredicate);
    }

    public static Class<?>[] getClasses(Object... objects) {
        return ClassUtil.getClasses(objects);
    }


    public static Object invoke(Object object, Method method, Object... args) {
        if (method.canAccess(object)) {
            method.setAccessible(true);
        }

        try {
            return method.invoke(ClassUtil.isStatic(method) ? null : object, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Caller getCaller() {
        Class<?> clz = null;
        Annotation annotation = null, annotationTemp;
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            try {
                clz = Class.forName(element.getClassName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            annotationTemp = AnnotateUtils.getAnnotation(clz, CallerSensitive.class);
            if (Objects.isNull(annotation)) {
                annotation = annotationTemp;
            } else if (Objects.isNull(annotationTemp)) {
                return new DefaultCaller(element);
            }
        }
        return null;
    }
}
