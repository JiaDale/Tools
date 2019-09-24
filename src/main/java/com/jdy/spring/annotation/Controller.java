package com.jdy.spring.annotation;

import java.lang.annotation.*;

/**
 * 页面交互
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 15:41
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
