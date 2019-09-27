package com.jdy.entity;


import java.io.Serializable;
import java.util.Map;

public interface Entity<T> extends Serializable, Getter<String>, Setter<String, T> {

    /**
     * 获取数据Map
     *
     * @return 存储数据的Map对象
     */
    Map<String, Object> getDataMap();


    Entity setDataMap(Map<String, Object> map);
}
