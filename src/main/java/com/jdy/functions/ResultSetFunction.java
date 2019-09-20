package com.jdy.functions;

import com.jdy.util.CloseUtil;
import com.jdy.util.TextUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

/**
 * 将数据查询结果转换成List集合， 由于是数据转换，所以实现了JDK1.8中的{@link Function}函数
 * <p>
 * 数据查询结果可能会有4种情况
 * <p>
 * <ol><li>结果为多列多行</li> <li>结果为多列单行</li><li>结果为单列多行</li><li>结果为单列单行</li> </ol>
 * <p>
 * 因此在转换过程中，不管结果是以上的那种情况将会返回一个{@link List}对象,当然也有可能查询结果为空，如果查询结果为空返回的结果将也会是空
 */
public class ResultSetFunction implements Function<ResultSet, List<Map<String, Object>>> {

    /**
     * 需要实现的方法
     *
     * @param resultSet 接收的参数为{@link ResultSet}对象
     * @return 转换后的集合
     */
    @Override
    public List<Map<String, Object>> apply(ResultSet resultSet) {
        //如果查询结果为空，直接返回null
        if (Objects.isNull(resultSet))
            return null;
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            //遍历查询结果
            while (resultSet.next()) { //纵向遍历，有多少条数据，循环多少次
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                Map<String, Object> map = new HashMap<>();
                for (int i = 0, index = i + 1; i < columnCount; i++, index++) {
                    //获取数据的列名
                    String columnName = metaData.getColumnName(index);
                    //对应列名的值
                    Object value = resultSet.getObject(index);
                    //如果列名为空或者字段值为空，则跳过此处循环
                    if (null == value || TextUtils.isEmpty(columnName))
                        continue;
                    //保存字段
                    map.put(columnName, value);
                }
                //如果map为空，将循环遍历到下一行
                if (map.isEmpty())
                    continue;
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(resultSet);
        }
        return list;
    }
}
