package com.jdy.spring.reader;

import java.util.Properties;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 3:25
 */
public interface Reader {

    /**
     * 1. 读取配置文件，返回一个配置文件的Properties对象
     *
     * @return Properties对象
     */
    Properties getProperties();
}
