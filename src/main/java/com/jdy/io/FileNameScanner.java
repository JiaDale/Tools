package com.jdy.io;

import com.jdy.util.ClassUtil;
import com.jdy.util.TextUtils;

import java.io.File;
import java.net.URL;

/**
 * Description: Tools
 * Created by Dale on 2019/9/21 14:12
 */
public class FileNameScanner extends FileScanner<String>{

    private boolean isWeb = false;

    private FileNameScanner(boolean isWeb) {
        this.isWeb = isWeb;
    }

    public static Scanner<File, String> create(boolean isWeb) {
        return new FileNameScanner(isWeb);
    }

    @Override
    protected String convert(String packageName, File file) {
        return packageName + "." + file.getName();
    }

    @Override
    protected URL createURL(String classPath) {
        if (TextUtils.isBlack(classPath)) {
            return null;
        }

        if (isWeb) {
            return ClassUtil.getClassLoader().getResource(classPath);
        }

        return this.getClass().getResource(classPath);
    }
}
