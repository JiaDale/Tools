package com.jdy.spring.factory;


import com.jdy.log.Log;
import com.jdy.spring.bean.BeanDefinition;
import com.jdy.spring.context.AbstractContext;
import com.jdy.spring.process.BeanPostProcessor;
import com.jdy.spring.process.Processor;
import com.jdy.spring.reader.BeanDefinitionReader;
import com.jdy.spring.reader.DefinitionReader;
import com.jdy.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 23:40
 */
public abstract class ListableBeanFactory extends AbstractContext implements BeanFactory {


    protected DefinitionReader reader;

    //存储注册信息的BeanDefinition
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    protected void refresh() throws Exception {
        //1、定位，定位配置文件
        reader = new BeanDefinitionReader(locationConfigs);

        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        Collection<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        //3、注册，把配置信息放到容器里面(伪IOC容器)
        if (CollectionUtils.isEmpty(beanDefinitions)) {
            return;
        }

        String simpleClassName;
        for (BeanDefinition beanDefinition : beanDefinitions) {
            simpleClassName = beanDefinition.getSimpleClassName();
            if (beanDefinitionMap.containsKey(simpleClassName)) {
                throw new Exception(String.format("此[%s]已经存在！", simpleClassName));
//                continue;
            }

            beanDefinitionMap.put(simpleClassName, beanDefinition);

            //4、把不是延时加载的类，有提前初始化
            if (!beanDefinition.isLazyInit()) {
                getBean(simpleClassName);
            }
        }
    }

    @Override
    public Properties getProperties() {
        return reader.getProperties();
    }

    @Override
    public Object getBean(String simpleClassName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(simpleClassName);

        Object instance = null;

        //这个逻辑还不严谨，自己可以去参考Spring源码
        //工厂模式 + 策略模式
        Processor processor = new BeanPostProcessor();

        processor.postProcessBeforeInitialization(null, simpleClassName);

        instance = instantiateBean(simpleClassName, beanDefinition);
        if (Objects.isNull(instance)) {
            Log.error("实例化类[%s]失败！", simpleClassName);
            return null;
        }

        processor.postProcessAfterInitialization(instance, simpleClassName);

        //3、注入
        populateBean(instance);

        return instance;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return  beanDefinitionMap.keySet().toArray(new String[0]);

    }

    protected abstract void populateBean(Object instance);


    protected abstract Object instantiateBean(String simpleClassName, BeanDefinition definition);
}
