package com.jdy.entity;

/**
 * 观察者模式， 被观察的接口
 *
 * <p>
 * 为了实时监控Entity对象是否存在变化，新建了观察者模式来监控
 * <p>
 * Entity对象中的数据的存储都是 K -> V 模式
 *
 * @param <K> 观察者需要跟新的数据中的 Key
 * @param <V> 观察者需要跟新的数据中的 Value
 */
public interface OnDataChangeListener<K, V> {

    /**
     * 此形式与Map相似，此方法也需要借助Map的性质来实现
     *
     * @param key   数据的Key值
     * @param value 数据的Value值
     */
    void onDataChange(K key, V value);


}
