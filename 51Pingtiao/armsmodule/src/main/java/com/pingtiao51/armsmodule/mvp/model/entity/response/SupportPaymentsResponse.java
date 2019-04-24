package com.pingtiao51.armsmodule.mvp.model.entity.response;

public class SupportPaymentsResponse {

    /**
     * alipay : 0
     * wechat : 1
     * bankcard : 1
     */

    private int alipay;
    private int wechat;
    private int bankcard;

    public int getAlipay() {
        return alipay;
    }

    public void setAlipay(int alipay) {
        this.alipay = alipay;
    }

    public int getWechat() {
        return wechat;
    }

    public void setWechat(int wechat) {
        this.wechat = wechat;
    }

    public int getBankcard() {
        return bankcard;
    }

    public void setBankcard(int bankcard) {
        this.bankcard = bankcard;
    }
}
