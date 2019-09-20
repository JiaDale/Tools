package com.jdy.functions;

import com.jdy.entity.BaseEntity;

import java.util.List;
import java.util.function.Function;

class ListFunction<T> implements Function<List<T>, List<BaseEntity>> {

    @Override
    public List<BaseEntity> apply(List<T> list) {
        if (null == list || list.isEmpty())
            return null;

        List<BaseEntity> entityList = null;
        for (T item : list) {
            if (item.getClass().isAssignableFrom(BaseEntity.class)) {

            }
        }
        return entityList;
    }
}
