package com.jdy.database;


import com.jdy.util.JDBCUtil;

public abstract class BaseQueryDAO implements QueryDAO {

    /**
     * 主要用来执行， 增加、修改和删除的SQL语句
     *
     * @param sql        SQL语句
     * @param parameters 参数
     * @return true ： 执行成功 ； false ： 执行失败
     */
    @Override
    public boolean execute(String sql, Object[] parameters) {
        return JDBCUtil.execute(JDBCUtil.getDataSource(), sql, parameters) > 0;
    }

//    /**
//     * 通过给定的sql和参数查询数据
//     *
//     * @param sql        SQL语句
//     * @param parameters 参数
//     * @param <T>        结果集合中元素类型
//     * @return 查询结果集合
//     */
//    @Override
//    public <T> List<T> select(String sql, Object[] parameters) {
//        ResultSetConsumer<T> consumer = new DefaultResultSetConsumer<>();
//        select(consumer, sql, parameters);
//        return consumer.getList();
//    }
//
//    /**
//     * 通过给定的sql和参数查询数据
//     *
//     * @param consumer   消费查询结果对象
//     * @param sql        SQL语句
//     * @param parameters 参数
//     * @param <T>        结果集合中元素类型
//     * @return 查询结果集合
//     */
//    private <T> void select(ResultSetConsumer<T> consumer, String sql, Object[] parameters) {
//        JDBCUtil.executeQuery(JDBCUtil.getDataSource(), consumer, sql, parameters);
//    }
}
