package com.jdy.spring.reader;

import com.jdy.functions.PredicateFunction;
import com.jdy.io.FileUtils;
import com.jdy.log.Log;
import com.jdy.spring.bean.BeanDefinition;
import com.jdy.util.ClassUtil;
import com.jdy.util.CloseUtil;
import com.jdy.util.CollectionUtils;
import com.jdy.util.TextUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 15:04
 */
public class BeanDefinitionReader extends AbstractDefinitionReader{
    private static final String SCAN_PACKAGE = "scanPackage";
    private final Collection<String> beanClasses;
    /**
     *  通过URL定位找到其所对应的文件，然后转换为文件流
     *
     * @param locationConfigs 配置文件名称
     */
    public BeanDefinitionReader(String... locationConfigs) {
        InputStream inputStream = null;
        try {
            for (String locationConfig : locationConfigs) {
                if (TextUtils.isBlack(locationConfig)) continue;
                inputStream = ClassUtil.getClassLoader().getResourceAsStream(locationConfig.replace("classpath:", ""));
                properties.load(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("读取配置文件[%s]失败！", String.join(", ", locationConfigs));
        } finally {
            CloseUtil.close(inputStream);
        }

        final String packageName = properties.getProperty(SCAN_PACKAGE);
        beanClasses = FileUtils.scanFiles(String.class, new PredicateFunction<>() {
            @Override
            public boolean test(File file) {
                return file.getName().endsWith(".class");
            }

            @Override
            public String apply(File file) {
                return TextUtils.substring(file.getAbsolutePath().replaceAll("[/\\\\]+", "."), packageName).replace(".class", "");
            }
        }, packageName);
    }

    @Override
    public Collection<BeanDefinition> loadBeanDefinitions() {
        if (CollectionUtils.isEmpty(beanClasses)) {
            Log.error("未从配置文件中扫描到可转换成BeanDefinition的文件信息");
            return null;
        }

        Collection<BeanDefinition> collection = new ArrayList<>();
        try {
            for (String beanClassName : beanClasses) {
                Class<?> beanClass = ClassUtil.forName(beanClassName);

                //如果是一个接口，是不能实例化的, 抽象类也不能实例化
                //用它实现类来实例化
                if (beanClass.isInterface()) continue;

                //beanName有三种情况:
                //1、默认是类名首字母小写
                //2、自定义名字
                //3、接口注入
                collection.add(createBean(TextUtils.lowerFristCase(beanClass.getSimpleName()), beanClass.getName()));

                for (Class<?> anInterface : beanClass.getInterfaces()) {
                    collection.add(createBean(TextUtils.lowerFristCase(anInterface.getSimpleName()), beanClass.getName()));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return collection;
    }

    private BeanDefinition createBean(String simpleClassName, String className) {
        return new BeanDefinition(simpleClassName, className);
    }
}
