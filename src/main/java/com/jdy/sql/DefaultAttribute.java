package com.jdy.sql;

import java.util.Collection;
import java.util.Set;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/17 20:54
 */
public class DefaultAttribute implements Attribute {
    private final String mTableName;
    private final Collection<String> mPrimaryKeys;
    private final Collection<String> mColumnNames;


    public DefaultAttribute(String tableName, Set<String> primaryKeys, Set<String> columnNames) {
        mTableName = tableName;
        mPrimaryKeys = primaryKeys;
        mColumnNames = columnNames;
    }

    @Override
    public String getTableName() {
        return mTableName;
    }

    @Override
    public Collection<String> getColumnNames() {
        return mColumnNames;
    }

    @Override
    public Collection<String> getPrimaryKeys() {
        return mPrimaryKeys;
    }
}
