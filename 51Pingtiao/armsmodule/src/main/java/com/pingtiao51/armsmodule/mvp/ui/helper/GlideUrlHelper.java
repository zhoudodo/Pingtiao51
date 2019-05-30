package com.pingtiao51.armsmodule.mvp.ui.helper;

import com.bumptech.glide.load.model.GlideUrl;

public class GlideUrlHelper extends GlideUrl {

    private String mUrl;

    public GlideUrlHelper(String url) {
        super(url);
        mUrl = url;
    }

    @Override
    public String getCacheKey() {
        return getCacheKeyReal();
    }

    private String getCacheKeyReal() {
        //获取特征缓存key 保证缓存一致不会重复刷新图片
        if (mUrl.contains("android") && mUrl.contains("pingtiao-prod.oss-cn-hangzhou.aliyuncs.com")) {
            String[] splitUrls = mUrl.split("\\?");
            if (splitUrls.length == 2) {
                if (splitUrls[1].contains("Expires") && splitUrls[1].contains("OSSAccessKeyId") && splitUrls[1].contains("Signature")) {
                    return splitUrls[0];
                }
            }
        }
        return mUrl;
    }

}
