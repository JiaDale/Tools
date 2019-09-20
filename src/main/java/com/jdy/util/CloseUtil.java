package com.jdy.util;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

import java.io.Closeable;
import java.sql.Blob;
import java.sql.Clob;


public class CloseUtil {
    private final static Log log = LogFactory.getLog(CloseUtil.class);

    public static void close(AutoCloseable autoCloseable) {
        if (autoCloseable == null) {
            return;
        }

        try {
            autoCloseable.close();
        } catch (Exception e) {
            log.debug("close error", e);
        }
    }

    public static void close(Closeable x) {
        if (x == null) {
            return;
        }

        try {
            x.close();
        } catch (Exception e) {
            log.debug("close error", e);
        }
    }

    public static void close(Blob x) {
        if (x == null) {
            return;
        }

        try {
            x.free();
        } catch (Exception e) {
            log.debug("close error", e);
        }
    }
    
    
    public static void close(Clob x) {
        if (x == null) {
            return;
        }

        try {
            x.free();
        } catch (Exception e) {
            log.debug("close error", e);
        }
    }
    
}
