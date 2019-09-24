package com.jdy.spring.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 17:53
 */
public class ModelView {
    //一个前台页面对应一个ModelView
    private final String viewName;

    private final Map<String,Object> model;

    public ModelView(String fileName) {
        this(fileName, new HashMap<>());
    }

    public ModelView(String viewName, Map<String, Object> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getViewModel() {
        return model;
    }
}
