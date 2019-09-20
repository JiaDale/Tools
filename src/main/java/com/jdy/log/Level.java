package com.jdy.log;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/17 13:08
 */
public enum Level {
    ERROR, INFO, TRACE, DEBUG, WARN;

    public java.util.logging.Level toJDK() {
        switch (this) {
            case TRACE:
                return java.util.logging.Level.FINEST;
            case DEBUG:
                return java.util.logging.Level.FINE;
            case INFO:
                return java.util.logging.Level.INFO;
            case WARN:
                return java.util.logging.Level.WARNING;
            case ERROR:
                return java.util.logging.Level.SEVERE;
            default:
                return createError();
        }
    }

    private java.util.logging.Level createError() {
        throw new Error("日志级别‘com.jdy.log.Level'中暂时没有这个级别【" + name() + "】");
    }
}
