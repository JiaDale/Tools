package com.jdy.io;

import com.jdy.util.TextUtils;

import java.io.InputStream;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/13 15:10
 */
public class ResourceUtil {

    public static InputStream getResourceStream(String resourceName) {
        if (TextUtils.isEmpty(resourceName)) {
            return null;
        }

        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
    }
}
