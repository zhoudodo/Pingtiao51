package com.pingtiao51.armsmodule.mvp.model.entity.response;

public class UserBankListResponse {

    /**
     * bankName : 招商银行
     * bankNo : 122121211111221
     * icon : bank_zhongg@3x.png
     * id : 1
     */

    private String bankName;
    private String bankNo;
    private String icon;
    private long id;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
