package com.jdy.design.proxy.dynamic;

/**
 * {describe}
 *
 * @Author : Dale
 * @Create on 2019/1/3 17:56
 * @Version 1.0
 */
public class BaseSubject implements Subject{
    @Override
    public void onSubject() {
        System.out.println("实现了Subject");
    }

}
