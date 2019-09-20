package com.jdy.sql;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/17 21:33
 */
public interface Expression {

    default String getTableName(){
        return getAttribute().getTableName();
    }

    boolean isValidSQL();

    String generateSQL();

    Object[] getConditionValues();

    Condition getCondition();

    Attribute getAttribute();
}
