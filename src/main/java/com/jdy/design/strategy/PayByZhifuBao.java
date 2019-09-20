package com.jdy.design.strategy;

public class PayByZhifuBao implements Pay {

    @Override
    public String getType() {
        return "支付宝";
    }
}
