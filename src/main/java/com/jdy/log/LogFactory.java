package com.jdy.log;


import com.alibaba.druid.support.logging.NoLoggingImpl;
import com.jdy.reflection.Caller;
import com.jdy.reflection.ReflectUtils;
import com.jdy.util.ClassUtil;


import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * Description: Tools
 * Created by Administrator on 2019/9/17 13:03
 */
public class LogFactory {
    private static Constructor logConstructor;

    static {
//        tryImplementation("org.slf4j.Logger", "com.jdy.log.jdk.SLF4JLogger");
//        tryImplementation("org.apache.log4j.Logger", "com.jdy.log.jdk.Log4jLogger");
//        tryImplementation("org.apache.logging.log4j.Logger", "com.jdy.log.jdk.Log4j2Logger");
//        tryImplementation("org.apache.commons.logging.LogFactory", "com.jdy.log.jdk.JakartaCommonsLoggingLogger");
        tryImplementation("java.util.logging.Logger", "com.jdy.log.jdk.JdkLogger");
        if (Objects.isNull(logConstructor)) {
            try {
                logConstructor = NoLoggingImpl.class.getConstructor(Caller.class);
            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }

    private static void tryImplementation(String className, String implClassName) {
        if (Objects.nonNull(logConstructor)) {
            Log.warn("已经初始化了" + LogFactory.class.getName() + "中的 logConstructor");
            return;
        }

        try {
            ClassUtil.forName(className);
            Class<?> aClass = ClassUtil.forName(implClassName);
            logConstructor = aClass.getConstructor(Caller.class);
            if (aClass.isAssignableFrom(Logger.class)) {
                logConstructor = null;
            }
        } catch (ClassNotFoundException e) {
            System.err.println("无法找到'" + className + "'!");
        } catch (NoSuchMethodException e) {
            System.err.println("此 '" + className + "'没有只含Caller参数的构造方法！");
        }
    }


    public static Logger createLogger() {
        Caller caller = ReflectUtils.getCaller();
        try {
            return (Logger) logConstructor.newInstance(caller);
        } catch (Throwable e) {
            throw new RuntimeException("Error creating logger for logger '" + caller.getCallerStackTrace().getClassName() + "'.  Cause: " + e, e);
        }
    }
}
