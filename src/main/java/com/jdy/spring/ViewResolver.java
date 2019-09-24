package com.jdy.spring;

import com.jdy.util.TextUtils;

import java.io.File;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 23:13
 */
public class ViewResolver {

    private final File templateRootDir;

    private final static String[] FILE_TYPE = {".html", ".xhtml"};

    public ViewResolver(File template) {
        templateRootDir = template;
    }

    public View resolveView(String viewName, Object o) {
        if (TextUtils.isBlack(viewName)) {
            return null;
        }

        if (TextUtils.removeSuffixIgnoreCase(viewName, FILE_TYPE).equals(TextUtils.removeSuffixIgnoreCase(templateRootDir.getName(), FILE_TYPE))) {
            return new View(templateRootDir);
        }

        return null;
    }
}
