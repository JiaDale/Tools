package com.jdy.database;


import com.jdy.entity.Entity;
import com.jdy.log.Log;
import com.jdy.sql.Expression;
import com.jdy.sql.entity.DeleteEntity;
import com.jdy.sql.entity.InsertEntity;
import com.jdy.sql.entity.UpdateEntity;
import com.jdy.util.JDBCUtil;
import com.jdy.util.TextUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


/**
 * Description: Service
 * Created by Administrator on 2019/7/18 23:20
 */
public class BaseExecuteQuery implements Service {

    private SimpleQueryDAO dao = new SimpleQueryDAO();

    @Override
    public boolean update(Entity entity) {
        if (Objects.isNull(entity)) {
            return false;
        }

        Expression expression;
        try {
            expression = new UpdateEntity(entity);
        } catch (IOException e) {
            Log.error("创建InsertEntity实例对象失败！");
            return false;
        }

        if (expression.isValidSQL()) {
            return delete(expression.generateSQL(), expression.getConditionValues());
        }

        Log.error("无效跟新SQL语句：" + TextUtils.completeSQL(expression.generateSQL(), expression.getConditionValues()));
        return false;
    }

    @Override
    public boolean insert(Entity entity) {
        if (Objects.isNull(entity)) {
            return false;
        }

        Expression expression;
        try {
            expression = new InsertEntity(entity);
        } catch (IOException e) {
            Log.error("创建InsertEntity实例对象失败！");
            return false;
        }

        if (expression.isValidSQL()) {
            return delete(expression.generateSQL(), expression.getConditionValues());
        }

        Log.error("无效插入SQL语句：" + TextUtils.completeSQL(expression.generateSQL(), expression.getConditionValues()));
        return false;
    }

    @Override
    public boolean delete(Entity entity) {
        if (Objects.isNull(entity)) {
            return false;
        }

        Expression expression;
        try {
            expression = new DeleteEntity(entity);
        } catch (IOException e) {
            Log.error("创建InsertEntity实例对象失败！");
            return false;
        }

        if (expression.isValidSQL()) {
            return delete(expression.generateSQL(), expression.getConditionValues());
        }

        Log.error("无效删除SQL语句：" + TextUtils.completeSQL(expression.generateSQL(), expression.getConditionValues()));
        return false;
    }

    @Override
    public boolean update(String sql, Object... parameters) {
        return JDBCUtil.execute(JDBCUtil.getDataSource(), sql, parameters) > 0;
    }

    @Override
    public boolean insert(String sql, Object... parameters) {
        return JDBCUtil.execute(JDBCUtil.getDataSource(), sql, parameters) > 0;
    }

    @Override
    public boolean delete(String sql, Object... parameters) {
        return JDBCUtil.execute(JDBCUtil.getDataSource(), sql, parameters) > 0;
    }

    @Override
    public boolean execute(String sql, Object... parameters) {
        return dao.execute(sql, parameters);
    }

    @Override
    public Query getQuery() {
        return this;
    }

    @Override
    public <T> List<T> select(String sql, Object... parameters) {
        return dao.select(sql, parameters);
    }

    @Override
    public <T> List<T> select(String sql, Class<T> claz, Object... objects) {
        return dao.select(sql, claz, objects);
    }
}
