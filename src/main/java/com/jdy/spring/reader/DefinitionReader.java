package com.jdy.spring.reader;

import com.jdy.spring.bean.BeanDefinition;

import java.util.Collection;

/**
 * 配置文件转换成BeanDefinition对象接口
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 0:04
 */
public interface DefinitionReader extends Reader {


    /**
     * 2.根据配置文件中的信息对文件夹进行扫描，并且将其转换成BeanDefinition对象，以便IOC操作
     *
     * @return BeanDefinition对象集合
     */
    Collection<BeanDefinition> loadBeanDefinitions();

}
