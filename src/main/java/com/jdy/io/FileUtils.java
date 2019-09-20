package com.jdy.io;

import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;

/**
 * 文件工具类
 *
 * Description: Tools
 * Created by Administrator on 2019/9/13 14:44
 */
public class FileUtils {

    /**
     * 获取.properties文件的函数对象
     * @param fileName 文件名称
     * @return 函数对象
     */
    public static Function<String, Object> getPropertyFunction(String fileName) {
        Properties properties = loadFile(fileName, ClassLoader.getSystemClassLoader());
        if (properties == null) {
            return s -> null;
        }
        return properties::get;
    }

    public static Properties loadFile(String fileName, ClassLoader loader) {
        return loadFile(fileName, loader, false);
    }

    public static Properties loadFile(String fileName, ClassLoader loader, boolean isEnd) {
        try {
            Properties properties = new Properties();
            properties.load(Objects.requireNonNull(loader.getResourceAsStream(fileName)));
            return properties;
        } catch (Exception ex) {
            if (isEnd) {
                return null;
            }
            return loadFile(fileName, Thread.currentThread().getContextClassLoader(), true);
        }
    }




}
