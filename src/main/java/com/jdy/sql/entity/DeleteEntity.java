package com.jdy.sql.entity;

import com.jdy.entity.Entity;
import com.jdy.sql.AttributeUtil;
import com.jdy.sql.BaseAttribute;
import com.jdy.util.TextUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/14 22:48
 */
public class DeleteEntity extends BaseAttribute {

    private Object[] columnValues;

    public DeleteEntity(Entity entity) throws IOException {
        super(AttributeUtil.apply(entity));
        Collection<Object> set = new LinkedHashSet<>();

        StringBuilder appendable = new StringBuilder();
        Iterator<String> iterator = mAttribute.getColumnNames().iterator();
        while (iterator.hasNext()) {
            String columnName = iterator.next();
            Object object = entity.get(columnName);
            if (Objects.isNull(object)) {
                continue;
            }
            conditionMap.put(columnName, object);
            set.add(object);
            if (TextUtils.isNotEmpty(appendable)) {
                appendable.append(' ').append("and").append(' ');
            }
            appendable.append(columnName).append('=').append('?');
        }
        columnStr = appendable.toString();
        columnValues = set.toArray(new Object[0]);
    }

    public String generateSQL() {
        return "Delete from " + mTableName + " where " + columnStr;
    }


    @Override
    public boolean isValidSQL() {
        return TextUtils.isNotBlack(mTableName) && TextUtils.isNotEmpty(columnStr);
    }

    @Override
    public Object[] getConditionValues() {
        return columnValues;
    }
}
