package com.pingtiao51.armsmodule.mvp.model.entity.response;

public class ShouHuanKuanResponse {

    /**
     * borrowAmonut : 1000
     * lendAmount : 1000
     */

    private String borrowAmonut;
    private String lendAmount;

    public String getBorrowAmonut() {
        return borrowAmonut;
    }

    public void setBorrowAmonut(String borrowAmonut) {
        this.borrowAmonut = borrowAmonut;
    }

    public String getLendAmount() {
        return lendAmount;
    }

    public void setLendAmount(String lendAmount) {
        this.lendAmount = lendAmount;
    }
}
