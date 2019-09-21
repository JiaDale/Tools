package com.jdy.io;


import com.jdy.functions.PredicateFunction;
import com.jdy.log.Log;
import com.jdy.util.ArrayUtil;
import com.jdy.util.ClassUtil;
import com.jdy.util.CollectionUtils;

import java.io.File;
import java.util.*;
import java.util.function.Function;

/**
 * 文件工具类
 * <p>
 * Description: Tools
 * Created by Administrator on 2019/9/13 14:44
 */
public class FileUtils {

    /**
     * 获取.properties文件的函数对象
     *
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
            return loadFile(fileName, ClassUtil.getClassLoader(), true);
        }
    }

    public static Collection<String> scanFiles(final String... packageNames) {
        return scanFiles(packageNames, false, null);
    }

    /**
     * 获取指定文件夹下的所有文件名称
     *
     * @return 返回扫描的文件夹下的所有文件
     */
    public static Collection<String> scanFiles(final String packageNames, boolean isWeb, PredicateFunction<File, String> predicate) {
        if (ArrayUtil.isEmpty(packageNames)) {
            Log.error("未指定扫描目录");
            return null;
        }
        Collection<String> files = null, temp;
        Scanner<File, String> scanner = FileNameScanner.create(isWeb);
        for (String packageName : packageNames) {
            temp = scanner.scanFiles(packageName, predicate).getFiles();
            if (CollectionUtils.isEmpty(files))
                files = temp;
            else files.addAll(temp);
        }
        return files;
    }
}

