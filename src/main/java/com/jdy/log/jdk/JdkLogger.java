package com.jdy.log.jdk;

import com.jdy.log.AbstractLogger;
import com.jdy.log.Level;
import com.jdy.reflection.Caller;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 */
public class JdkLogger extends AbstractLogger {

    private final Logger logger;

    public JdkLogger(Caller caller) {
        super(caller);
        logger = Logger.getLogger(getLogName());
    }

    @Override
    public void log(Level level, Throwable throwable, String format, Object... args) {
        LogRecord record = new LogRecord(level.toJDK(), String.format(format, args));
        record.setLoggerName(getLogName());
        record.setThrown(throwable);
        record.setSourceClassName(caller.getCallerStackTrace().getClassName());
        record.setSourceMethodName(caller.getCallerStackTrace().getMethodName());
        logger.log(record);
    }
}
