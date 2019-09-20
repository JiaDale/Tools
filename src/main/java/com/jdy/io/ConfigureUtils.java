package com.jdy.io;

import com.jdy.util.JDBCUtil;
import com.jdy.util.TextUtils;

import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/13 15:28
 */
public final class ConfigureUtils {

    private static final Map<String, ResourceBundle> bundleMap;

    /**
     *  默认数据库配置文件名称
     */
    public static final String CONFIG_JDBC = "jdbc";

    /**
     * 默认加载工具时初始化配置文件中的内容
     */
    static {
        bundleMap = new TreeMap<>();
        getResourceBundle(CONFIG_JDBC);
    }


    private static ResourceBundle getResourceBundle(String configFileName) {
        if (TextUtils.isEmpty(configFileName)) {
            return null;
        }

        ResourceBundle bundle = bundleMap.get(configFileName);
        if (Objects.isNull(bundle)) {
            bundle = ResourceBundle.getBundle(configFileName);
            if (!Objects.isNull(bundle))
                bundleMap.put(configFileName, bundle);
        }

        return bundle;
    }

    public static String getConfigValue(String  configFileName, String configKey){
        ResourceBundle bundle = getResourceBundle(configFileName);
        if (bundle == null) {
            return null;
        }

        String configValue = bundle.getString(configKey);
        if (TextUtils.isBlack(configValue)){
            return null;
        }

        return configValue.trim();
    }

    public static String getConfigValue(String configFileName, String configKey, String defaultIfNull){
        return TextUtils.checkValue(getConfigValue(configFileName, configKey), defaultIfNull);
    }

    public static String getConfigJDBC(String configKey){
        return getConfigJDBC(configKey, "");
    }

    public static String getConfigJDBC(String configKey, String defaultIfNull){
        return getConfigValue(CONFIG_JDBC, configKey, defaultIfNull);
    }

    public static boolean isMySQL(){
        return "MySQL".equalsIgnoreCase(JDBCUtil.getDataSource().getDbType());
    }

}
