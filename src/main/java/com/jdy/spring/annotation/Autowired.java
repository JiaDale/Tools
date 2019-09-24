package com.jdy.spring.annotation;

import java.lang.annotation.*;

/**
 * 自动注入
 * <p>
 *
 * <p>
 * 创建人 Dale 时间 2019/9/22 15:40
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    String value() default "";

}
