package com.pingtiao51.armsmodule.mvp.model.entity.response;

import com.pingtiao51.armsmodule.mvp.model.entity.response.banner.BannerParentInterface;

public class HomeBannerResponse implements BannerParentInterface {

    /**
     * detailUrl : 12211
     * picUrl : 12211
     * type : H5
     */

    private String detailUrl;//调转地址
    private String picUrl;
    private String type;//调转的类型 H5 或者PIC 图片

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getLoadUrl() {
        return picUrl;
    }

    @Override
    public String getClickIntentUrl() {
        return "H5".equals(type) ? detailUrl:null;
    }

    @Override
    public int getResId() {
        return 0;
    }
}
