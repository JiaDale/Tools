package com.jdy.functions;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/19 0:14
 */
class DefaultResultSetConsumer<T> implements ResultSetConsumer<T> {

    private final List<T> mList;

    DefaultResultSetConsumer() {
        mList = new ArrayList<>();
    }

    @Override
    public List<T> getList() {
        return mList;
    }

    @Override
    public void accept(T result) {
        if (Objects.isNull(result)) {
            return;
        }
        mList.add(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T apply(ResultSet resultSet) {
        if (Objects.isNull(resultSet)) {
            return null;
        }

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (columnCount < 1) {//如果查询结果少于1列，返回null
                return null;
            }

            if (columnCount > 1) {//如果大于1列，证明结果是一个map，返回一个Entity对象
                Map<String, Object> map = new HashMap<>();
                for (int i = 0, index = i + 1; i < columnCount; i++, index++) {
                    Object object = resultSet.getObject(index);
                    if (Objects.isNull(object)) {
                        continue;
                    }
                    map.put(metaData.getColumnName(index), object);
                }
//                return Entity.create(map) ;
            }
            //单个数据类型，直接强制返回
            return (T) resultSet.getObject(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
