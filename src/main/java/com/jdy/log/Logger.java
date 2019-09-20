package com.jdy.log;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/17 13:05
 */
public interface Logger {

    default void log(String format, Object... args){
        log(null, format, args);
    }
    
    void log(Throwable t, String format, Object... args);

    void log(Level level, Throwable throwable, String format, Object... args);

}
