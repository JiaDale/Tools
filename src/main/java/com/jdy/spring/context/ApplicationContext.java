package com.jdy.spring.context;

import com.jdy.annotation.AnnotateUtils;
import com.jdy.spring.annotation.Autowired;
import com.jdy.spring.annotation.Controller;
import com.jdy.spring.annotation.Service;
import com.jdy.spring.bean.BeanDefinition;
import com.jdy.spring.factory.ListableBeanFactory;
import com.jdy.spring.wrapper.BeanWrapper;
import com.jdy.spring.wrapper.Wrapper;
import com.jdy.util.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 14:57
 */
public class ApplicationContext extends ListableBeanFactory{

    //单例的IOC容器缓存
    private Map<String, Object> singletonMap = new ConcurrentHashMap<>();

    //通用的IOC容器
    private Map<String, Wrapper> wrapperMap = new ConcurrentHashMap<>();

    public ApplicationContext(String... locationConfigs) {
        this.locationConfigs = locationConfigs;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  初始化所扫描到的所有类，或者说将扫描到的所有类初始化
     *
     *
     * 此框架还存在一个问题，抽象类是没法自己实例化的，
     *
     *
     * @param simpleClassName 扫描到的类名
     * @param beanDefinition 配置
     * @return
     */
    @Override
    protected Object instantiateBean(String simpleClassName, BeanDefinition beanDefinition) {
        //1、拿到要实例化的对象的类名
        String className = beanDefinition.getClassName();

        //假设默认就是单例,细节暂且不考虑，先把主线拉通
        if (singletonMap.containsKey(className)) {
            return singletonMap.get(className);
        }

        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
            /**
             * 抽象类在这里会存在问题
             *
             * 抽象类无法自己实例化，因此这里需要过滤掉， 或者前面就做好预防
             */
            Constructor<?> constructor = getNoParameterConstructor(Class.forName(className));
            if (Objects.isNull(constructor)){
                return null;
            }
            instance = constructor.newInstance();
            singletonMap.put(className, instance);
            singletonMap.put(simpleClassName, instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //3、把这个对象封装到BeanWrapper中
        wrapperMap.put(simpleClassName, new BeanWrapper(instance));

        return instance;
    }

    private Constructor<?> getNoParameterConstructor(Class<?> claz) {
        if (Objects.isNull(claz)) return null;

        for (Constructor<?> constructor : claz.getConstructors())
            if (constructor.getParameterCount() < 1) return constructor;

        return null;
    }

    @Override
    protected void populateBean(Object instance) {
        if (Objects.isNull(instance)){//如果初始化没有成功，返回
            return;
        }

//        Wrapper wrapper = wrapperMap.get(simpleClassName);
//        Object instance = wrapper.getWrappedInstance();
        Class<?> clazz = instance.getClass();

        //判断只有加了注解的类，才执行依赖注入
        if (!(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class))) {
            return;
        }

        Wrapper wrapper;
        //获得所有的fields
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Autowired.class)) continue;

            wrapper = wrapperMap.get(TextUtils.checkValue(AnnotateUtils.getAnnotationValue(field, Autowired.class), field.getType().getName()));

            if (Objects.isNull(wrapper)) continue;

            //强制访问
            field.setAccessible(true);
            try {//Autowired注解的成员变量，在此处将初始化，实例化
                field.set(instance, wrapper.getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
