package com.jdy.spring.annotation;

import java.lang.annotation.*;

/**
 * 请求参数映射
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 15:43
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    String value() default "";

    boolean required() default true;

}
