package com.jdy.util;

import java.util.*;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/14 12:25
 */
public class CollectionUtils {

    /**
     * 新建一个HashSet
     *
     * @param <T> 集合元素类型
     * @param elements 元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... elements) {
        return newHashSet(false, elements);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T> 集合元素类型
     * @param isSorted 是否有序，有序返回 {@link LinkedHashSet}，否则返回 {@link HashSet}
     * @param elements 元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> newHashSet(boolean isSorted, T... elements) {
        if (null == elements) {
            return CheckUtil.checkValue( new HashSet<>(), isSorted, new LinkedHashSet<>());
        }
        int initialCapacity = Math.max((int) (elements.length / .75f) + 1, 16);
        HashSet<T> set = isSorted ? new LinkedHashSet<T>(initialCapacity) : new HashSet<T>(initialCapacity);
        Collections.addAll(set, elements);
        return set;
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return null == collection || collection.isEmpty();
    }

    public static <K, V> boolean isEmpty(Map<K,V> collection) {
        return null == collection || collection.isEmpty();
    }
}
