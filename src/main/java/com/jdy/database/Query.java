package com.jdy.database;

import com.jdy.util.CollectionUtils;

import java.util.List;

/**
 * Description: SQL查询接口，此接口主要定义了查询功能
 * Created by Administrator on 2019/9/13 17:29
 */
public interface Query {

    /**
     * 通过给定的sql和参数查询数据
     *
     * @param sql     SQL语句
     * @param claz    查询结果数据对象的class
     * @param objects 参数
     * @param <T>     结果集合中元素类型
     * @return 查询结果集合
     */
    <T> List<T> select(String sql, Class<T> claz, Object... objects);

    /**
     * 通过给定的sql和参数查询单条数据
     * <p>
     * 此方法的实现依赖{@link #select(String, Class, Object...)}方法，默认会取其第一条记录
     *
     * @param sql     SQL语句
     * @param claz    查询结果数据对象的class
     * @param objects 参数
     * @param <T>     查询结果数据的数据类型
     * @return 单条查询结果参数
     */
    default <T> T query(String sql, Class<T> claz, Object... objects) {
        List<T> list = select(sql, claz, objects);

        if (CollectionUtils.isEmpty(list)) {//如果没有查询到值，直接返回空
            return null;
        }
        //取第一条数据
        return list.get(0);
    }

}
