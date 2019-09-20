package com.jdy.database;

import com.jdy.util.CollectionUtils;

import java.util.List;

/**
 * Description: 数据库SQL执行DAO接口
 * Created by Administrator on 2019/9/13 16:49
 */
public interface DataAccessObject {

    /**
     * 主要用来执行， 增加、修改和删除的SQL语句
     *
     * @param sql        SQL语句
     * @param parameters 参数
     * @return true ： 执行成功 ； false ： 执行失败
     */
    boolean execute(String sql, final Object... parameters);

    /**
     * 通过给定的sql和参数查询数据
     *
     * @param sql        SQL语句
     * @param parameters 参数
     * @param <T>        结果集合中元素类型
     * @return 查询结果集合
     */
    <T> List<T> select(String sql, final Object... parameters);

    /**
     * 通过给定的sql和参数查询单条数据
     * <p>
     * 此方法的实现依赖{@link #select(String, Object...)}方法，默认会取其第一条记录
     *
     * @param sql        SQL语句
     * @param parameters 参数
     * @param <T>        查询结果数据的数据类型
     * @return 单条查询结果参数
     */
    default <T> T query(String sql, final Object... parameters) {
        List<T> list = select(sql, parameters);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
