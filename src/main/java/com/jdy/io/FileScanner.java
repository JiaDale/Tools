package com.jdy.io;

import com.jdy.functions.PredicateFunction;
import com.jdy.log.Log;
import com.jdy.util.ArrayUtil;
import com.jdy.util.TextUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Description: Tools
 * Created by Dale on 2019/9/21 14:01
 */
public abstract class FileScanner<T> implements Scanner<File, T> {

    private final Collection<T>  mCollection = new ArrayList<>();


    @SuppressWarnings("unchecked")
    public static <T> Scanner<File,T> create(Class<T> type) {
        if (type == String.class){
            return (Scanner<File, T>) new FileNameScanner();
        }

        if (type ==  File.class){
            return (Scanner<File, T>) new DefaultFileScanner();
        }

        throw new UnsupportedOperationException(String.format("暂时不支持扫描文件以%s类型输出", type.getClass().getName()));
    }

    @Override
    public Scanner<File, T> scanFiles(String packageName, PredicateFunction<File, T> function) throws MalformedURLException {
        if (TextUtils.isBlack(packageName)) {
            Log.warn("请确认文件包名: %s", packageName);
        }

        URL url = FileUtils.searchFilePath(packageName).toUri().toURL();

        File[] files = new File(url.getFile()).listFiles();
        if (ArrayUtil.isEmpty(files)) {
            Log.warn("-------------文件夹%s下没有文件-------------", url.getFile());
            return this;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                scanFiles(file.getAbsolutePath(), function);
            } else if (Objects.isNull(function)) {
                mCollection.add(convert(packageName, file));
            } else if (function.test(file)) {
                mCollection.add(function.apply(file));
            }
        }
        return this;
    }

    protected abstract T convert(String packageName, File file);

    @Override
    public Collection<T> getFiles() {
        return mCollection;
    }
}
