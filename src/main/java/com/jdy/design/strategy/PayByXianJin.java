package com.jdy.design.strategy;

public class PayByXianJin implements Pay {

    @Override
    public String getType() {
        return "现金";
    }
}
