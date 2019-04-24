package com.pingtiao51.armsmodule.mvp.model.entity.response;

import com.pingtiao51.armsmodule.mvp.model.entity.response.banner.BannerParentInterface;

public class CopyBanner implements BannerParentInterface {

    private int resId;
    public CopyBanner(int resId){
        this.resId = resId;
    }

    @Override
    public String getLoadUrl() {
        return null;
    }

    @Override
    public String getClickIntentUrl() {
        return null;
    }

    @Override
    public int getResId() {
        return resId;
    }
}
