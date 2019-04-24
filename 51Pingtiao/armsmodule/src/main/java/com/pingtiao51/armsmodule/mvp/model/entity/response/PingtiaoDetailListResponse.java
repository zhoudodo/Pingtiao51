package com.pingtiao51.armsmodule.mvp.model.entity.response;

import java.util.List;

public class PingtiaoDetailListResponse {


    /**
     * borrowAmount :
     * list : [{"amount":1000,"borrowAndLendState":0,"borrower":"张三","hasCloudStoreOrConfirm":"CLOUD_STORE","id":1,"lender":"李四","overDueDays":10,"repaymentDate":"2019-02-11","status":"OVER_DUE","totalAmount":1000,"type":"OWE_NOTE"}]
     * retAmount :
     * total : 100
     */

    private String borrowAmount;
    private String retAmount;
    private int total;
    private List<PingtiaoDetailResponse> list;

    public String getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(String borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getRetAmount() {
        return retAmount;
    }

    public void setRetAmount(String retAmount) {
        this.retAmount = retAmount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PingtiaoDetailResponse> getList() {
        return list;
    }

    public void setList(List<PingtiaoDetailResponse> list) {
        this.list = list;
    }

}
