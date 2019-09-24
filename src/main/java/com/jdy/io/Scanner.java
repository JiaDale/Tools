package com.jdy.io;


import com.jdy.functions.PredicateFunction;

import java.net.MalformedURLException;
import java.util.Collection;

/**
 * 常用工具包
 * <p>
 * [文件扫描器]， 根据指定的文件路径扫描当前目录下的所有文件
 * <p>
 * 创建人 Dale 时间 2019/9/21 12:53
 */
public interface Scanner<T, R> {

    Scanner<T, R> scanFiles(final String packageName, PredicateFunction<T, R> function) throws MalformedURLException;

    Collection<R> getFiles();
}
