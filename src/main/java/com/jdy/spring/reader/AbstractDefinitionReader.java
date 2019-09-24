package com.jdy.spring.reader;

import java.util.Properties;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 0:06
 */
public abstract class AbstractDefinitionReader implements DefinitionReader {

    protected Properties properties  = new Properties();

    @Override
    public Properties getProperties() {
        return properties;
    }
}
