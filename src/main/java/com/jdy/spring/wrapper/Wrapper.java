package com.jdy.spring.wrapper;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 14:23
 */
public interface Wrapper {

    Object getWrappedInstance();

    Class<?> getWrappedClass();
}
