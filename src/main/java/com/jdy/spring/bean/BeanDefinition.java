package com.jdy.spring.bean;

/**
 * 常用工具包
 * <p>
 * 类的包装器，
 * <p>
 * 此类主要用于将扫描到的类文件，保存类的文件名称，和其他属性，比如此类是否是延迟加载，此类是否是单例对象类
 * <p>
 * 后期可以根据类名进行实例化
 * <p>
 * 创建人 Dale 时间 2019/9/21 23:35
 */
public class BeanDefinition {

    private String simpleClassName;
    private String className;
    private boolean isLazyInit;
    private boolean isSingleton = true;

    /**
     * @param simpleClassName 类文件的简单名称
     * @param className 类文件的 class 全名
     */
    public BeanDefinition(String simpleClassName, String className) {
        this.simpleClassName = simpleClassName;
        this.className = className;
    }


    public String getSimpleClassName() {
        return simpleClassName;
    }

    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isLazyInit() {
        return isLazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        isLazyInit = lazyInit;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }
}
