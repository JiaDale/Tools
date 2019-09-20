package com.jdy.sql;

import com.jdy.annotation.AnnotateUtils;
import com.jdy.annotation.Column;
import com.jdy.annotation.PrimaryKey;
import com.jdy.annotation.Table;
import com.jdy.entity.Entity;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/17 20:32
 */
public class AttributeUtil {

    public static Attribute apply(Entity entity) {
        return new DefaultAttribute(AnnotateUtils.getAnnotationValue(entity.getClass(), Table.class), AnnotateUtils.getAnnotationValueFromMethods(entity.getClass(), Column.class, PrimaryKey.class), AnnotateUtils.getAnnotationValueFromMethods(entity.getClass(), Column.class));
    }
}
