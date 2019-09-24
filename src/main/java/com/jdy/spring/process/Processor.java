package com.jdy.spring.process;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 1:04
 */
public interface Processor {

    Object postProcessBeforeInitialization(Object bean, String beanName);


    Object postProcessAfterInitialization(Object bean, String beanName);
}
