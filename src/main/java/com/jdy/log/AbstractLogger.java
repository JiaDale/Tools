package com.jdy.log;


import com.jdy.reflection.Caller;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/17 15:41
 */
public abstract class AbstractLogger implements Logger {

    protected final Caller caller;

    public AbstractLogger(Caller caller) {
        this.caller = caller;
    }

    @Override
    public void log(Throwable t, String format, Object... args) {
        log(Level.INFO, t, format, args);
    }

    protected String getLogName() {
        return caller.getCallerStackTrace().getClassName();
    }
}
