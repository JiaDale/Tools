package com.jdy.design.proxy;

/**
 * 明星X
 *
 * @Author : Dale
 * @Create on 2018/12/29 16:11
 * @Version 1.0
 */
public class Singer extends Teamer implements Sing {

    Singer() {
    }

       @Override
    public void sing() {
        System.out.print("G3歌曲");
    }

    @Override
    public void display() {
        System.out.println("负责唱歌");
    }
}
