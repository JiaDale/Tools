package com.jdy.sql.entity;

import com.jdy.entity.Entity;
import com.jdy.sql.AttributeUtil;
import com.jdy.sql.BaseAttribute;
import com.jdy.util.TextUtils;

import java.io.IOException;
import java.util.*;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/14 22:48
 */
public class UpdateEntity extends BaseAttribute {

    private Object[] columnValues;
    private String primaryKeyStr;


    public UpdateEntity(Entity entity) throws IOException {
        super(AttributeUtil.apply(entity));

        List<Object> list = new LinkedList<>();
        Collection<String> keys = new TreeSet<>();
        StringBuilder appendable = new StringBuilder();
        Iterator<String> iterator = mAttribute.getPrimaryKeys().iterator();
        while (iterator.hasNext()) {
            String columnName = iterator.next();
            Object object = entity.get(columnName);
            if (Objects.isNull(object)) {
                continue;
            }
            conditionMap.put(columnName, object);
            keys.add(columnName);
            list.add(object);
            if (TextUtils.isNotEmpty(appendable)) {
                appendable.append(',').append(' ');
            }
            appendable.append(columnName).append('=').append('?');
        }
        primaryKeyStr = appendable.toString();

        appendable = new StringBuilder();
        int index = 0;
        iterator = mAttribute.getColumnNames().iterator();
        while (iterator.hasNext()) {
            String columnName = iterator.next();
            Object object = entity.get(columnName);
            if (Objects.isNull(object) || keys.contains(columnName)) {
                continue;
            }
            conditionMap.put(columnName, object);
            list.add(index, object);
            if (TextUtils.isNotEmpty(appendable)) {
                appendable.append(',').append(' ');
            }
            appendable.append(columnName).append('=').append('?');
        }
        columnStr = appendable.toString();
        columnValues = list.toArray(new Object[0]);
    }

    public String generateSQL() {
        return "Update " + mTableName + " set " + columnStr + " where " + primaryKeyStr;
    }

    @Override
    public boolean isValidSQL() {
        return TextUtils.isNotBlack(mTableName) && TextUtils.isNotEmpty(columnStr) && TextUtils.isNotEmpty(primaryKeyStr);
    }

    @Override
    public Object[] getConditionValues() {
        return columnValues;
    }
}
