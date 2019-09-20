package com.jdy.design.observe;


import java.util.concurrent.Flow;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/18 16:04
 */
public interface UpdatePublisher<K, V, T> extends Flow.Publisher<T> {

    void update(K key, V value);

}
