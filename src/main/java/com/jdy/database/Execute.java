package com.jdy.database;

/**
 * Description: SQL执行接口，
 * <p>
 * 此接口提供了SQL在执行新增、修改和删除操作可以调用的方法
 * <p>
 * Created by Administrator on 2019/7/17 0:39
 */
public interface Execute {

    /**
     * 执行数据跟新操作，调用此方法
     *
     * @param sql  SQL语句
     * @param objects  参数
     * @return true ：执行成功；false ： 执行失败
     */
    boolean update(String sql, Object... objects);

    /**
     * 执行数据新增操作，调用此方法
     *
     * @param sql  SQL语句
     * @param objects  参数
     * @return true ：执行成功；false ： 执行失败
     */
    boolean insert(String sql, Object... objects);

    /**
     * 执行数据删除操作，调用此方法
     *
     * @param sql  SQL语句
     * @param objects  参数
     * @return true ：执行成功；false ： 执行失败
     */
    boolean delete(String sql, Object... objects);

}
