package com.jdy.design.strategy;

public class Bargain {

    public void bargain(Pay pay) {
        System.out.print("使用" + pay.getType() +"付款");
    }

}
