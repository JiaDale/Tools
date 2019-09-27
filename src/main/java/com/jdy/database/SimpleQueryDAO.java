package com.jdy.database;


import com.jdy.entity.Entity;
import com.jdy.util.CollectionUtils;
import com.jdy.util.JDBCUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description: Service
 * Created by Administrator on 2019/7/17 0:21
 */
public class SimpleQueryDAO extends BaseQueryDAO implements Query {


    private <T> T convert(Map<String, Object> map, Class<T> tClass) {
        if (Objects.isNull(tClass)) {
            int size = map.size();

            if (size > 1) {
                try {
                    return (T) map;
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            if (size < 1) {
                return null;
            }

            return (T) map.values().iterator().next();
        }

        /**
         * 这里发现一个问题， 子类的class对象 调用 isAssignableFrom 父类的 class 对象 竟然可能为false
         *
         *  按理来说 子类找父类 应该比父类找子类好找。。。。
         *
         */
        if (Entity.class.isAssignableFrom(tClass)) {
            return ProxyEntityFactory.create(tClass, map);
        }

        if (Map.class.isAssignableFrom(tClass)) {
            return (T) map;
        }

        for (Object next : map.values()) {
            if (next.getClass().equals(tClass)) {
                return (T) next;
            }
        }

        throw new UnsupportedOperationException("暂时不支持数据：" + map + " 向 " + tClass.getName() + "数据类型转换！");
    }





    @Override
    public <T> List<T> select(String sql, final Class<T> claz, Object... parameters) {
        List<Map<String, Object>> list = JDBCUtil.executeQuery(JDBCUtil.getDataSource(), sql, parameters);
        if (CollectionUtils.isEmpty(list))
            return null;

        T newInstance;
        //将查询结果进行转换，转换成需要的数据类型，
        List<T> retList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            newInstance = convert(map, claz);
            if (null == newInstance) {  //如果newInstance为空，直接跳过
                continue;
            }
            retList.add(newInstance);
        }
        return retList;
    }

    @Override
    public Query getQuery() {
        return this;
    }
}
