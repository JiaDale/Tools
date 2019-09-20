package com.jdy.design.proxy;

/**
 * {describe}
 *
 * @Author : Dale
 * @Create on 2018/12/29 16:51
 * @Version 1.0
 */
public class Perfomer extends Teamer implements Sing, Dance {
    Perfomer() {
    }

    @Override
    public void dance() {
        System.out.print("W2舞蹈");
    }

    @Override
    public void sing() {
        System.out.print("G1歌");
    }

    @Override
    public void display() {
        System.out.println("负责唱歌跳舞");
    }
}
