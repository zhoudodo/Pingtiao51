package com.pingtiao51.armsmodule.mvp.model.entity.response;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class PingtiaoDetailResponse implements MultiItemEntity {

    public final static int DIANZI_JIETIAO = 0;
    public final static int DIANZI_SHOUTIAO = 1;
    public final static int ZHIZHI_JIETIAO = 2;
    public final static int ZHIZHI_SHOUTIAO = 3;
    /**
     * amount : 1000
     * borrowAndLendState : 0
     * borrower : 张三
     * hasCloudStoreOrConfirm : CLOUD_STORE
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
    private String hasCloudStoreOrConfirm;
    private long id;
    private String lender;
    private String overDueDays;
    private String repaymentDate;
    private String status;
    private String signStatus;
    private String totalAmount;
    private String type;
    private String viewUrl;
    private String hasApplyRepayRecord;


    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
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

    public String getHasCloudStoreOrConfirm() {
        return hasCloudStoreOrConfirm;
    }

    public void setHasCloudStoreOrConfirm(String hasCloudStoreOrConfirm) {
        this.hasCloudStoreOrConfirm = hasCloudStoreOrConfirm;
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

    public String getHasApplyRepayRecord() {
        return hasApplyRepayRecord;
    }

    public void setHasApplyRepayRecord(String hasApplyRepayRecord) {
        this.hasApplyRepayRecord = hasApplyRepayRecord;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public int itemType = DIANZI_JIETIAO;
}
