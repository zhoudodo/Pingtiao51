package com.pingtiao51.armsmodule.mvp.model.entity.response;

import java.util.List;

public class PingtiaoXiangqingResponse {


    /**
     * amount : 1000
     * borrower : 李四
     * comment : 好啊
     * createTime : 2019-10-02
     * downUrl : \2019\03\12\201903121558550000000001NT13..jpg
     * hasCloudStoreOrConfirm : CLOUD_STORE
     * id : 1
     * lender : 李四
     * loanDate : 2019-10-02
     * loanUsage : 购物
     * noteNo : 201903121558550000000001NT02
     * repaymentDate : 2019-10-02
     * status : DRAFT
     * totalInterest : 100.21
     * urls : C:\Users\mi\Desktop\test\pic\2019\03\12\201903121558550000000001NT03..jpg
     * viewPdfUrl : \2019\03\12\201903121558550000000001NT13..jpg
     * yearRate : 0.3
     */

    private String amount;
    private String borrower;
    private String comment;
    private String createTime;
    private String downUrl;
    private String hasCloudStoreOrConfirm;
    private int id;
    private String lender;
    private String loanDate;
    private String loanUsage;
    private String noteNo;
    private String repaymentDate;
    private String status;
    private String totalInterest;
    private List<String> urls;
    private String viewPdfUrl;
    private String yearRate;
    private String initiator;
    private OwnershipRecordDto  ownershipRecordDto;

    public OwnershipRecordDto getOwnershipRecordDto() {
        return ownershipRecordDto;
    }

    public void setOwnershipRecordDto(OwnershipRecordDto ownershipRecordDto) {
        this.ownershipRecordDto = ownershipRecordDto;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getHasCloudStoreOrConfirm() {
        return hasCloudStoreOrConfirm;
    }

    public void setHasCloudStoreOrConfirm(String hasCloudStoreOrConfirm) {
        this.hasCloudStoreOrConfirm = hasCloudStoreOrConfirm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getLoanUsage() {
        return loanUsage;
    }

    public void setLoanUsage(String loanUsage) {
        this.loanUsage = loanUsage;
    }

    public String getNoteNo() {
        return noteNo;
    }

    public void setNoteNo(String noteNo) {
        this.noteNo = noteNo;
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

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getViewPdfUrl() {
        return viewPdfUrl;
    }

    public void setViewPdfUrl(String viewPdfUrl) {
        this.viewPdfUrl = viewPdfUrl;
    }

    public String getYearRate() {
        return yearRate;
    }

    public void setYearRate(String yearRate) {
        this.yearRate = yearRate;
    }



    public static class OwnershipRecordDto{

        /**
         * certificateDate : 2019-03-01
         * certificateStructure : 杭州互联网公证处
         * ownershipOrderNo : e221e1e1d1e1
         */

        private String certificateDate;
        private String certificateStructure;
        private String ownershipOrderNo;

        public String getCertificateDate() {
            return certificateDate;
        }

        public void setCertificateDate(String certificateDate) {
            this.certificateDate = certificateDate;
        }

        public String getCertificateStructure() {
            return certificateStructure;
        }

        public void setCertificateStructure(String certificateStructure) {
            this.certificateStructure = certificateStructure;
        }

        public String getOwnershipOrderNo() {
            return ownershipOrderNo;
        }

        public void setOwnershipOrderNo(String ownershipOrderNo) {
            this.ownershipOrderNo = ownershipOrderNo;
        }
    }



}
