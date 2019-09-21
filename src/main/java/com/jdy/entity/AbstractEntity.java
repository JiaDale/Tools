package com.jdy.entity;

import com.jdy.design.observe.Observable;
import com.jdy.design.observe.Observer;
import com.jdy.util.CollectionUtils;
import com.jdy.util.TextUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/20 2:16
 */
abstract class AbstractEntity implements Entity, Observable<String, Object> {

    private static final long serialVersionUID = -2061950544706597370L;

    /**
     * 数据存储对象
     */
    private Map<String, Object> dataMap;
    private final Map<String, String> keyMap = new TreeMap<>();
    private Collection<Observer<String, Object>> observers;

    /**
     * 无参构造函数，实例化数据存储对象
     */
    AbstractEntity() {
        this(new ConcurrentHashMap<>());
    }

    AbstractEntity(Map<String, Object> map) {
        this.dataMap = map;
    }


    @Override
    public String toString() {
        Appendable sb = new StringBuilder();
        try {
            sb.append('{');
            Iterator<Map.Entry<String, Object>> iterator = dataMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                if (Objects.isNull(value)) {
                    continue;
                }
                sb.append(key).append("=").append(String.valueOf(value));
                if (!iterator.hasNext())
                    break;
                sb.append(',').append(' ');
            }
            return sb.append('}').toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entity)) return false;
        Map<String, Object> targetMap = ((Entity) obj).getDataMap();

        /**
         * 对应的是同一个对象
         */
        if (targetMap == dataMap) {
            return true;
        }

        /**
         * 长度不一样，直接放回 false
         */
        if (dataMap.size() != targetMap.size())
            return false;

        if (dataMap.isEmpty()) return targetMap.isEmpty();

        Collection<String> keyCollection = new HashSet<>();
        Iterator<Map.Entry<String, Object>> iterator = targetMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            Object currentValue = get(key);
            keyCollection.add(key.toUpperCase());
            if (Objects.isNull(value)) {
                if (Objects.isNull(currentValue)) {
                    continue;
                }
                return false;
            }

            if (!value.equals(currentValue)) {
                return false;
            }
        }
        Set<String> keySet = dataMap.keySet();
        keyCollection.addAll(keySet.stream().map(String::toUpperCase).collect(Collectors.toSet()));
        return keyCollection.size() == keySet.size();
    }

    @Override
    public void update(String key, Object value) {
        if (CollectionUtils.isEmpty(observers)) {
            return;
        }

        Iterator<Observer<String, Object>> iterator = observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().update(key, value);
        }
    }

    @Override
    public void subscribe(Observer<String, Object> observer) {
        if (CollectionUtils.isEmpty(observers)) {
            observers = CollectionUtils.newHashSet(observer);
        } else {
            observers.add(observer);
        }
    }

    @Override
    public int hashCode() {
        return dataMap.hashCode();
    }

    /**
     * 取数据
     * <p>
     * 备注：此方法存在数据转换问题， 因此调用时尽量让其返回Object对象
     *
     * @param key 字段名称
     * @param <V> 字段值的数据类型
     * @return 字段值
     */
    @SuppressWarnings("unchecked")
    @Override
    public <V> V get(final String key) {
        String tempKey = keyMap.get(key.toUpperCase());
        if (TextUtils.isBlack(tempKey)) {
            return (V) dataMap.get(key);
        }
        return (V) dataMap.get(tempKey);
    }

    /**
     * 获取数据， 结合数据库字段名称, 可以忽略大小写的性质，因此这里做个兼容
     * <p>
     * 调用{@link Setter#set(Object, Object)}方法设值，默认会忽略大小写问题(即字符串“AbCd”与“abcd”是等价的)
     * <p>
     * 需要考虑大小写问题，则会出现：
     * <pre>
     *      key = "AbCd" ,  value = "1";
     *      key = "abcd",   value = "2";
     * <pre/>
     *
     *  即顺序一样不同的大小写组合可以不同的值
     *
     * @param key        字段名称
     * @param value      字段值
     * @param ignoreCase 忽略大小写问题
     * @param <V>        字段值数据类型
     * @return 不管设置成功还是失败，都会返回Setter，实现流式设值
     */
    @Override
    public <V> Setter set(final String key, V value, boolean ignoreCase) {
        if (TextUtils.isBlack(key)) {
            throw new IllegalArgumentException("Key值不能为空！");
        }

        String tempKey;
        if (ignoreCase) { //不需要考虑大小写问题，需要从keyMap获取第一次存储的key
            String upperKey = key.toUpperCase();
            tempKey = keyMap.get(upperKey);
            if (TextUtils.isBlack(tempKey)) {//如果没有取到，则证明数据存储对象中没有存储这条数据
                keyMap.put(upperKey, key);
                dataMap.put(tempKey = key, value);
            } else {
                dataMap.put(tempKey, value);
            }
        } else {
            dataMap.put(tempKey = key, value);
        }
        update(tempKey, value);
        return this;
    }

    @Override
    public Map<String, Object> getDataMap() {
        return dataMap;
    }
}
