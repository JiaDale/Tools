package com.jdy.database;

import com.jdy.entity.Entity;
import com.jdy.io.PropertiesUtil;
import com.jdy.util.ClassUtil;

import java.util.List;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/24 1:13
 */
public class AbstractService implements Service{

    private final Service dataAccessObject;

    public AbstractService() {
        Service dao;
        String daoClass = PropertiesUtil.getStr("com.jdy.database", "com.jdy.database.BaseExecuteQuery");
        try {
            dao = ProxyDAOFactory.create(ClassUtil.forName(daoClass));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            dao = new BaseExecuteQuery();
        }
        dataAccessObject = dao;
    }

    @Override
    public boolean execute(String sql, Object... parameters) {
        return dataAccessObject.execute(sql, parameters);
    }

    @Override
    public boolean update(Entity entity) {
        return dataAccessObject.update(entity);
    }

    @Override
    public boolean insert(Entity entity) {
        return dataAccessObject.insert(entity);
    }

    @Override
    public boolean delete(Entity entity) {
        return dataAccessObject.delete(entity);
    }

    @Override
    public boolean update(String sql, Object... objects) {
        return dataAccessObject.update(sql, objects);
    }

    @Override
    public boolean insert(String sql, Object... objects) {
        return dataAccessObject.insert(sql, objects);
    }

    @Override
    public boolean delete(String sql, Object... objects) {
        return dataAccessObject.delete(sql, objects);
    }

    @Override
    public <T> List<T> select(String sql, Class<T> claz, Object... objects) {
        return dataAccessObject.select(sql, claz, objects);
    }

    @Override
    public Query getQuery() {
        return this;
    }
}
