package com.jdy.io;

import com.jdy.functions.PredicateFunction;
import com.jdy.log.Log;
import com.jdy.util.ArrayUtil;
import com.jdy.util.TextUtils;

import java.io.File;
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

    @Override
    public Scanner<File, T> scanFiles(String packageName, PredicateFunction<File, T> function) {
        if (TextUtils.isBlack(packageName)) {
            Log.error("请确认文件包名: %s", packageName);
        }

        URL url = createURL("/" + packageName.replaceAll("\\.", "/"));

        if (Objects.isNull(url)) {
            Log.error("系统无法识别文件包名[%s]！", packageName);
            return this;
        }

        File[] files = new File(url.getFile()).listFiles();
        if (ArrayUtil.isEmpty(files)) {
            Log.error("文件夹%s下没有文件", packageName);
            return this;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                scanFiles(packageName + "." + file.getName(), function);
            } else if (Objects.isNull(function)) {
                mCollection.add(convert(packageName, file));
            } else if (function.test(file)) {
                mCollection.add(function.apply(file));
            }
        }
        return this;
    }

    protected abstract T convert(String packageName, File file);

    protected abstract URL createURL(String s);

    @Override
    public Collection<T> getFiles() {
        return mCollection;
    }
}
