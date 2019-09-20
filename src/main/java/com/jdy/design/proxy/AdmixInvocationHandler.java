package com.jdy.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * {describe}
 *
 * @Author : Dale
 * @Create on 2018/12/29 17:57
 * @Version 1.0
 */
public class AdmixInvocationHandler implements InvocationHandler {
    private Team admixer;

    public AdmixInvocationHandler(Team team) {
        this.admixer = admixer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用代理类");
        if ("admix".equals(method.getName())){
            System.out.println("调用的是admix方法");
        }
        return method.invoke(admixer, args);
    }
}
