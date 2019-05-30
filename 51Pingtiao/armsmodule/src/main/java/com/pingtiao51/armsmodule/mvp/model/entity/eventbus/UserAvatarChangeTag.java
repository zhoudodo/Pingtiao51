package com.pingtiao51.armsmodule.mvp.model.entity.eventbus;

//用户更换头像动作
public class UserAvatarChangeTag {
    private String loadUrl;
    public UserAvatarChangeTag(String url){
        loadUrl = url;
    }

    public String getLoadUrl() {
        return loadUrl;
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }
}
