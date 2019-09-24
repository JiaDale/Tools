package com.jdy.spring.factory;

/**
 * 单例工厂的顶层设计
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 23:37
 */
public interface BeanFactory {

    Object getBean(String simpleClassName);

}
