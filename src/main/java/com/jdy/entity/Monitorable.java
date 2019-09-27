package com.jdy.entity;

/**
 * 观察者模式， 被观察的接口
 * <p>
 * 为了实时监控Entity对象是否存在变化，新建了观察者模式来监控
 * <p>
 * Entity对象中的数据的存储都是 K -> V 模式
 *
 * @param <K> 观察者需要跟新的数据中的 Key
 * @param <V> 观察者需要跟新的数据中的 Value
 */
public interface Monitorable<K, V> {

    /**
     * 但数据发生修改变化时，会调用此方法，来通知观察者修改了那些数据
     *
     * @param key   观察者收到修改数据的key值
     * @param value 观察者收到数据的value值
     */
    void monitor(K key, V value);

    /**
     * 被观察者订阅观察者，调用此方法
     * <p>
     * 被观察者将会将所有观察者收集起来，当有数据跟新时会统一通知观察者们
     *
     * @param observer 观察者接口
     */
    default void addListener(OnDataChangeListener<K, V> observer){
        //此方法可选择是否实现
    }
}
