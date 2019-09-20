package com.jdy.reflection;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/17 17:47
 */
public interface Caller {

    /**
     * 获得调用者栈信息
     *
     * @return 调用者栈信息
     */
    StackTraceElement getCallerStackTrace();


}
