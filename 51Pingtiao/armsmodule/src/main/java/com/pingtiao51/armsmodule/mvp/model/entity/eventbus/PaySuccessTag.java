package com.pingtiao51.armsmodule.mvp.model.entity.eventbus;

public class PaySuccessTag {
    public final static int PAY_SUCCESS =0;
    private int type = 0;
    public PaySuccessTag(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
