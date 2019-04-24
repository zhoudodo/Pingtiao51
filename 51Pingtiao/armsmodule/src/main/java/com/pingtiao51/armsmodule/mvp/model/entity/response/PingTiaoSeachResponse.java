package com.pingtiao51.armsmodule.mvp.model.entity.response;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class PingTiaoSeachResponse implements MultiItemEntity {

    /**
     * amount : 1000
     * borrowAndLendState : 0
     * borrower : 张三
     * hasStored : 1
     * id : 1
     * lender : 李四
     * overDueDays : 10
     * repaymentDate : 2019-02-11
     * status : OVER_DUE
     * totalAmount : 1000
     * type : OWE_NOTE
     */

    private String amount;
    private String borrowAndLendState;
    private String borrower;
    private String hasStored;
    private long id;
    private String lender;
    private String overDueDays;
    private String repaymentDate;
    private String status;
    private String totalAmount;
    private String type;

    private boolean hasAnli;

    public boolean isHasAnli() {
        return hasAnli;
    }

    public void setHasAnli(boolean hasAnli) {
        this.hasAnli = hasAnli;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBorrowAndLendState() {
        return borrowAndLendState;
    }

    public void setBorrowAndLendState(String borrowAndLendState) {
        this.borrowAndLendState = borrowAndLendState;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getHasStored() {
        return hasStored;
    }

    public void setHasStored(String hasStored) {
        this.hasStored = hasStored;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getOverDueDays() {
        return overDueDays;
    }

    public void setOverDueDays(String overDueDays) {
        this.overDueDays = overDueDays;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int getItemType() {
        if ("OWE_NOTE".equals(type) || "PAPPER_OWE_NOTE".equals(type)) {
            //代还
            return JIE_STATUS;
        } else if ("PAPER_RECEIPT_NOTE".equals(type) || "RECEIPT_NOTE".equals(type)) {
            //代收
            return SHOU_STATUS;
        }

        return JIE_STATUS;
    }


    public final static int SHOU_STATUS = 0;
    public final static int JIE_STATUS = 1;

}
