package com.jdy.entity;

import com.jdy.util.CheckUtil;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/18 0:34
 */
public interface Getter<T> {

    <V> V get(T key);

    default <V> V get(T key, V defaultValue) {
        return CheckUtil.checkValue(get(key), defaultValue);
    }

}
