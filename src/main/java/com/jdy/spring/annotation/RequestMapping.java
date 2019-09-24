package com.jdy.spring.annotation;

import java.lang.annotation.*;

/**
 * url请求
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 15:42
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
