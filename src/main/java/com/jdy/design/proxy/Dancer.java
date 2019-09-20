package com.jdy.design.proxy;

/**
 * {describe}
 *
 * @Author : Dale
 * @Create on 2018/12/29 16:48
 * @Version 1.0
 */
public class Dancer extends Teamer implements Dance {
    Dancer() {
    }

    @Override
    public void dance() {
        System.out.print("W1舞蹈");
    }

    @Override
    public void display() {
        System.out.println("负责表演舞蹈");
    }
}
