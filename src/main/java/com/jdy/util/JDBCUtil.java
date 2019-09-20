package com.jdy.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.jdy.functions.ResultSetConsumer;
import com.jdy.functions.ResultSetFunction;
import com.jdy.io.ConfigureUtils;
import com.jdy.log.Log;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 数据库 连接工具
 * <p>
 * 其主要作用就是 获取数据库连接 和 关闭数据库连接
 */
public class JDBCUtil {

    public final static String DRIVER_CLASS_NAME = "driverClassName";
    public final static String URL = "url";
    public final static String USER_NAME = "username";
    public final static String PASSWORD = "password";

    public final static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public final static String MYSQL_URL = "jdbc:mysql://localhost:3306/base?&useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
    public final static String MYSQL_USER = "root";
    public final static String MYSQL_PASSWORD = "123456";

    private static Function<ResultSet, List<Map<String, Object>>> resultSetFunction = new ResultSetFunction();
    private static DruidDataSource mDataSource;

    /**
     * 获取连接
     *
     * @return 连接实例
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    /**
     * 获取数据库连接资源
     * <p>
     * 1.初始化数据库连接配置
     *
     * @return 连接资源
     */
    public static DruidDataSource getDataSource() {
        if (mDataSource == null) {
            mDataSource = new DruidDataSource();
            mDataSource.setDriverClassName(ConfigureUtils.getConfigJDBC(DRIVER_CLASS_NAME, MYSQL_DRIVER));
            mDataSource.setUrl(ConfigureUtils.getConfigJDBC(URL, MYSQL_URL));
            mDataSource.setUsername(ConfigureUtils.getConfigJDBC(USER_NAME, MYSQL_USER));
            mDataSource.setPassword(ConfigureUtils.getConfigJDBC(PASSWORD, MYSQL_PASSWORD));
        }
        return mDataSource;
    }


    public static int execute(DataSource dataSource, String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            return execute(connection, sql, parameters);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            CloseUtil.close(connection);
        }
    }

    public static int execute(Connection connection, String sql, Object... parameters) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            setParameters(statement, parameters);
            int update = statement.executeUpdate();
            if (update < 1) {
                Log.error("SQL执行未执行成功，  SQL ： " + TextUtils.completeSQL(sql, parameters));
            }
            return update;
        } catch (SQLException e) {
            Log.error("SQL执行错误，  SQL ： " + TextUtils.completeSQL(sql, parameters));
            System.err.println(statement);
            e.printStackTrace();
            return 0;
        } finally {
            CloseUtil.close(statement);
        }
    }

    public static List<Map<String, Object>> executeQuery(DataSource dataSource, String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            return executeQuery(connection, sql, parameters);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtil.close(connection);
        }
    }

    public static List<Map<String, Object>> executeQuery(Connection connection, String sql, Object... parameters) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            setParameters(statement, parameters);
            return resultSetFunction.apply(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtil.close(statement);
        }
    }

    /**
     * 这是一种思路，将查询结果用{@link ResultSetConsumer}来处理，
     *
     * @param dataSource 连接资源
     * @param consumer   消费者接口对象
     * @param sql        查询的SQL语句
     * @param parameters 参数
     * @param <T>        消费者所处理的数据类型
     */
    @Deprecated
    public static <T> void executeQuery(DataSource dataSource, ResultSetConsumer<T> consumer, String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            executeQuery(connection, consumer, sql, parameters);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(connection);
        }
    }

    @Deprecated
    public static <T> void executeQuery(Connection connection, ResultSetConsumer<T> consumer, String sql, Object[] parameters) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            setParameters(statement, parameters);
            convert(statement.executeQuery(), consumer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(statement);
        }
    }

    @Deprecated
    public static <T> void convert(ResultSet resultSet, ResultSetConsumer<T> consumer) {
        if (consumer == null || resultSet == null) {
            return;
        }
        try {
            while (resultSet.next()) {
                consumer.accept(consumer.apply(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(resultSet);
        }
    }

    private static void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }
}
