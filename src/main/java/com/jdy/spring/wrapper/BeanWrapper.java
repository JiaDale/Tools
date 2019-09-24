package com.jdy.spring.wrapper;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 1:55
 */
public class BeanWrapper implements Wrapper{

    private final Object wrappedInstance;

    public BeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    @Override
    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    @Override
    public Class<?> getWrappedClass() {
        return wrappedInstance.getClass();
    }
}
