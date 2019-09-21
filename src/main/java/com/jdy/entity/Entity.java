package com.jdy.entity;

import java.io.Serializable;
import java.util.Map;

public interface Entity extends Serializable, Getter<String>, Setter<String> {


    /**
     * 提供一个将Map转换成Entity实例对象的接口
     *
     * @param map Map数据
     * @param <T> Entity的子类数据类型
     * @return Entity的实例
     */
    @SuppressWarnings("unchecked")
    static <T extends Entity> T create(Map<String, Object> map) {
        //Collection<String> collection = FileUtils.scanFiles(Entity.class.getPackage().getName());

        return (T) new BaseEntity(map);
    }

    /**
     * 获取数据Map
     *
     * @return 存储数据的Map对象
     */
    Map<String, Object> getDataMap();

}
