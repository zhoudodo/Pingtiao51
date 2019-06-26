package com.pingtiao51.armsmodule.mvp.model.entity.eventbus;

/**
 * Created by dodozhou on 2017/6/7.
 */
public class WechatLoginTag {
    public final static int WX_LOGIN_CODE = 0;
    public int type = 0;


    public String code = "";

    public WechatLoginTag(int type) {
        this.type = type;
    }


    public WechatLoginTag(String code) {
        type = WechatLoginTag.WX_LOGIN_CODE;
        this.code = code;
    }
}
