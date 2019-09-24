package com.jdy.spring.context;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 23:33
 */
public abstract class AbstractContext implements Context {

    protected String[] locationConfigs;

    protected abstract void refresh() throws Exception;

}
