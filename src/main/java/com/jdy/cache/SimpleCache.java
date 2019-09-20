package com.jdy.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.Supplier;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/14 15:07
 */
public class SimpleCache<K, V> implements Serializable {

    private static final long serialVersionUID = -4041885362365578282L;

    /**
     * 池
     */
    private final Map<K, V> cache = new WeakHashMap<>();


    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private final ReadLock readLock = cacheLock.readLock();
    private final WriteLock writeLock = cacheLock.writeLock();

    /**
     * 从缓存池中查找值
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        // 尝试读取缓存
        readLock.lock();
        try {
            return cache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 从缓存中获得对象，当对象不在缓存中或已经过期返回Func0回调产生的对象
     *
     * @param key 键
     * @param supplier 如果不存在回调方法，用于生产值对象
     * @return 值对象
     */
    public V get(K key, Supplier<V> supplier) {
        V value = get(key);
        if (Objects.isNull(value)){
            if (Objects.isNull(supplier)){
                return null;
            }
            return put(key,supplier.get());
        }
        return value;
    }

    public V get(K key, V defaultIfNull){
        V value = get(key);
        if (Objects.isNull(value)){
            return put(key, defaultIfNull);
        }
        return value;
    }

    /**
     * 放入缓存
     * @param key 键
     * @param value 值
     * @return 值
     */
    public V put(K key, V value){
        writeLock.lock();
        try {
            cache.put(key, value);
        } finally {
            writeLock.unlock();
        }
        return value;
    }

    /**
     * 移除缓存
     *
     * @param key 键
     * @return 移除的值
     */
    public V remove(K key) {
        writeLock.lock();
        try {
            return cache.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 清空缓存池
     */
    public void clear() {
        writeLock.lock();
        try {
            this.cache.clear();
        } finally {
            writeLock.unlock();
        }
    }
}
