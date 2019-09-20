package com.jdy.annotation;

import com.jdy.reflection.ReflectUtils;
import com.jdy.util.ArrayUtil;


import java.lang.annotation.*;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/14 10:57
 */
public class AnnotateUtils {

    /**
     * 获取指定注解
     *
     * @param <A>            注解类型
     * @param annotationEle  {@link AnnotatedElement}，可以是Class、Method、Field、Constructor、ReflectPermission
     * @param annotationType 注解类型
     * @return 注解对象
     */
    public static <A extends Annotation> A getAnnotation(AnnotatedElement annotationEle, Class<A> annotationType) {
        return (null == annotationEle) ? null : toCombination(annotationEle).getAnnotation(annotationType);
    }

    @SafeVarargs
    public static <T> Set<T> getAnnotationValueFromFields(Class<?> clazz, Class<? extends Annotation> annotationType, Class<? extends Annotation>... annotationTypes) {
        Set<T> set = new HashSet<>();
        for (Field field : ReflectUtils.getFields(clazz)) {
            if (presentAnnotations(field, ArrayUtil.append(annotationTypes, annotationType))) {
                set.add(getAnnotationValue(field, annotationType));
            }
        }
        return set;
    }

    @SafeVarargs
    public static <T> Set<T> getAnnotationValueFromMethods(Class<?> clazz, Class<? extends Annotation> annotationType, Class<? extends Annotation>... annotationTypes) {
        Set<T> set = new HashSet<>();
        for (Method method : ReflectUtils.getMethods(clazz)) {
            if (presentAnnotations(method, ArrayUtil.append(annotationTypes, annotationType))) {
                set.add(getAnnotationValue(method, annotationType));
            }
        }
        return set;
    }

    @SafeVarargs
    private static boolean presentAnnotations(AnnotatedElement annotatedElement, Class<? extends Annotation>... annotationTypes) {
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            if (!annotatedElement.isAnnotationPresent(annotationType)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取指定注解默认值<br>
     * 如果无指定的属性方法返回null
     *
     * @param <T>            注解值类型
     * @param annotationEle  {@link AccessibleObject}，可以是Class、Method、Field、Constructor、ReflectPermission
     * @param annotationType 注解类型
     * @return 注解对象
     */
    public static <T> T getAnnotationValue(AnnotatedElement annotationEle, Class<? extends Annotation> annotationType) {
        return getAnnotationValue(annotationEle, annotationType, "value");
    }

    /**
     * 获取指定注解属性的值<br>
     * 如果无指定的属性方法返回null
     *
     * @param <T>            注解值类型
     * @param annotationEle  {@link AccessibleObject}，可以是Class、Method、Field、Constructor、ReflectPermission
     * @param annotationType 注解类型
     * @param propertyName   属性名，例如注解中定义了name()方法，则 此处传入name
     * @return 注解对象
     */
    public static <T> T getAnnotationValue(AnnotatedElement annotationEle, Class<? extends Annotation> annotationType, String propertyName) {
        final Annotation annotation = getAnnotation(annotationEle, annotationType);
        if (null == annotation) {
            return null;
        }

        final Method method = ReflectUtils.getMethod(annotation, propertyName);
        if (null == method) {
            return null;
        }

        return (T) ReflectUtils.invoke(annotation, method);
    }

    /**
     * 获取指定注解中所有属性值<br>
     * 如果无指定的属性方法返回null
     *
     * @param annotationEle  {@link AnnotatedElement}，可以是Class、Method、Field、Constructor、ReflectPermission
     * @param annotationType 注解类型
     * @return 注解对象
     */
    public static Map<String, Object> getAnnotationValueMap(AnnotatedElement annotationEle, final Class<? extends Annotation> annotationType) {
        final Annotation annotation = getAnnotation(annotationEle, annotationType);
        if (null == annotation) {
            return null;
        }

        final Method[] methods = ReflectUtils.getMethods(annotationType, method -> {
            if (ArrayUtil.isEmpty(method.getParameterTypes())) {//获取无参方法
                final String name = method.getName();
                if ("hashCode".equals(name) || "toString".equals(name) || "annotationType".equals(name)) {
                    return false;
                }
                return true;
            }
            return false;
        });

        final HashMap<String, Object> result = new HashMap<>(methods.length, 1);
        for (Method method : methods) {
            result.put(method.getName(), ReflectUtils.invoke(annotation, method));
        }
        return result;
    }


    /**
     * 获取注解类的保留时间，可选值 SOURCE（源码时），CLASS（编译时），RUNTIME（运行时），默认为 CLASS
     *
     * @param annotationType 注解类
     * @return 保留时间枚举
     */
    public static RetentionPolicy getRetentionPolicy(Class<? extends Annotation> annotationType) {
        final Retention retention = annotationType.getAnnotation(Retention.class);
        if (null == retention) {
            return RetentionPolicy.CLASS;
        }
        return retention.value();
    }

    /**
     * 获取注解类可以用来修饰哪些程序元素，如 TYPE, METHOD, CONSTRUCTOR, FIELD, PARAMETER 等
     *
     * @param annotationType 注解类
     * @return 注解修饰的程序元素数组
     */
    public static ElementType[] getTargetType(Class<? extends Annotation> annotationType) {
        final Target target = annotationType.getAnnotation(Target.class);
        if (null == target) {
            return new ElementType[]{ElementType.TYPE, //
                    ElementType.FIELD, //
                    ElementType.METHOD, //
                    ElementType.PARAMETER, //
                    ElementType.CONSTRUCTOR, //
                    ElementType.LOCAL_VARIABLE, //
                    ElementType.ANNOTATION_TYPE, //
                    ElementType.PACKAGE//
            };
        }
        return target.value();
    }

    /**
     * 是否会保存到 Javadoc 文档中
     *
     * @param annotationType 注解类
     * @return 是否会保存到 Javadoc 文档中
     */
    public static boolean isDocumented(Class<? extends Annotation> annotationType) {
        return annotationType.isAnnotationPresent(Documented.class);
    }

    /**
     * 是否可以被继承，默认为 false
     *
     * @param annotationType 注解类
     * @return 是否会保存到 Javadoc 文档中
     */
    public static boolean isInherited(Class<? extends Annotation> annotationType) {
        return annotationType.isAnnotationPresent(Inherited.class);
    }


    /**
     * 将指定的被注解的元素转换为组合注解元素
     *
     * @param annotationEle 注解元素
     * @return 组合注解元素
     */
    public static CombinationAnnotationElement toCombination(AnnotatedElement annotationEle) {
        if (annotationEle instanceof CombinationAnnotationElement) {
            return (CombinationAnnotationElement) annotationEle;
        }
        return new CombinationAnnotationElement(annotationEle);
    }


}
