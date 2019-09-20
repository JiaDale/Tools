package com.jdy.sql;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/14 22:17
 */
public abstract class BaseAttribute implements Expression, Condition {

    protected final Attribute mAttribute;
    protected final String mTableName;
    protected String columnStr;
    protected final Map<String, Object> conditionMap = new LinkedHashMap<>();


    protected BaseAttribute(Attribute attribute) {
        mAttribute = attribute;
        mTableName = attribute.getTableName();
    }

    @Override
    public Condition getCondition() {
        return this;
    }

    @Override
    public Map<String, Object> getConditionMap() {
        return conditionMap;
    }

    @Override
    public Attribute getAttribute() {
        return mAttribute;
    }
}
