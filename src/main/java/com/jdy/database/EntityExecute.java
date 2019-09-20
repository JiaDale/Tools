package com.jdy.database;

import com.jdy.entity.Entity;

/**
 * Description: SQL执行接口，此接口是对接口{@link Execute}接口的拓展，新增了对新增、删除和修改的另一种实现方式
 *
 * Created by Administrator on 2019/7/17 0:40
 */
public interface EntityExecute extends Execute{

    /**
     * 执行数据库修改操作，调用此方法
     *
     * @param entity 修改的Entity对象
     * @return true ：执行成功；false ： 执行失败
     */
    boolean update(Entity entity);

    /**
     * 执行数据库修改操作，调用此方法
     *
     * @param entity 修改的Entity对象
     * @return true ：执行成功；false ： 执行失败
     */
    boolean insert(Entity entity);

    /**
     * 执行数据库修改操作，调用此方法
     *
     * @param entity 修改的Entity对象
     * @return true ：执行成功；false ： 执行失败
     */
    boolean delete(Entity entity);

}
