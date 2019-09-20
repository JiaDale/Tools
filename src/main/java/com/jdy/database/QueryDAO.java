package com.jdy.database;

import java.util.List;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/20 1:24
 */
public interface QueryDAO extends DataAccessObject {

    Query getQuery();

    @Override
    default <T> List<T> select(String sql, final Object... parameters) {
        return getQuery().select(sql, null, parameters);
    }
}
