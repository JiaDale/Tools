package com.jdy.spring.context;

import com.jdy.spring.factory.BeanFactory;
import com.jdy.spring.reader.Reader;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 23:28
 */
public interface Context extends Reader, BeanFactory {

    String[] getBeanDefinitionNames();

}
