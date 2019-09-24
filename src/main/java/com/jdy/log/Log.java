package com.jdy.log;

import com.jdy.annotation.CallerSensitive;

@CallerSensitive
public final class Log {
    /**
     * Error等级日志<br>
     *
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void error(String format, Object... arguments) {
        error(null, format, arguments);
    }

    /**
     * Error等级日志<br>
     *
     * @param e         需在日志中堆栈打印的异常
     * @param format    格式文本
     * @param arguments 变量对应的参数
     */
    public static void error(Throwable e, String format, Object... arguments) {
        LogFactory.createLogger().log(Level.ERROR, e, format, arguments);
    }

    /**
     * Warn等级日志<br>
     *
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void warn(String format, Object... arguments) {
        warn(null, format, arguments);
    }

    /**
     * Warn等级日志<br>
     *
     * @param e         需在日志中堆栈打印的异常
     * @param format    格式文本
     * @param arguments 变量对应的参数
     */
    public static void warn(Throwable e, String format, Object... arguments) {
        LogFactory.createLogger().log(Level.WARN, e, format, arguments);
    }

    /**
     * Info等级日志<br>
     *
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void info(String format, Object... arguments) {
        info(null, format, arguments);
    }

    /**
     * Info等级日志<br>
     *
     * @param e         需在日志中堆栈打印的异常
     * @param format    格式文本
     * @param arguments 变量对应的参数
     */
    public static void info(Throwable e, String format, Object... arguments) {
        LogFactory.createLogger().log(Level.INFO, e, format, arguments);
    }
}
