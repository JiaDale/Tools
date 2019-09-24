package com.jdy.spring.factory;


import com.jdy.spring.bean.BeanDefinition;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 23:41
 */
public class DefaultListableBeanFactory extends ListableBeanFactory {

    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    protected void populateBean(Object instance) {

    }

    @Override
    protected Object instantiateBean(String simpleClassName, BeanDefinition definition) {
        return null;
    }

}
