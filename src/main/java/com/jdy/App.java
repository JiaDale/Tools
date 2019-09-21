package com.jdy;

import com.jdy.entity.BaseEntity;

import java.lang.reflect.Constructor;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 16:55
 */
public class App {
    public static void main(String[] args) {
        for (Constructor<?> constructor : BaseEntity.class.getConstructors()) {
            System.out.println(constructor);

        }
    }
}
