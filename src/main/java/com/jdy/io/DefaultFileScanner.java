package com.jdy.io;

import java.io.File;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 11:45
 */
public class DefaultFileScanner extends FileScanner<File> {

    @Override
    protected File convert(String packageName, File file) {
        return file;
    }
}
