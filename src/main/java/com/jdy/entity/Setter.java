package com.jdy.entity;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/18 0:42
 */
public interface Setter<T, R> {

    default <V> Setter set(T key, V value) {
        return set(key, value, true);
    }

    <V> Setter set(T key, V value, boolean ignoreCase);

    R build();
}
