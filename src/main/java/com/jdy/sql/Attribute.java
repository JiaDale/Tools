package com.jdy.sql;

import java.util.Collection;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/14 22:18
 */

public interface Attribute {

    String getTableName();

    Collection<String> getColumnNames();

    Collection<String> getPrimaryKeys();

}
