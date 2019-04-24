package com.pingtiao51.armsmodule.mvp.ui.helper;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Glide代理 by yuchaoliang on 16/4/19.
 */
public class GlideProxyHelper {

    /**
     * 加载本地图片
     * @param mImageView
     * @param ids
     */
    public static void loadImgForRes(ImageView mImageView, int ids) {
        Glide.with(mImageView.getContext()).load(ids).into(mImageView);
    }


    /**
     * 加载网络图片
     * @param mImageView
     * @param url
     */
    public static void loadImgForUrl(ImageView mImageView, String url) {
        Glide.with(mImageView.getContext()).load(url).into(mImageView);
    }

    /**
     * 占位图  网络图片加载
     * @param imageView
     * @param placeholderId
     * @param url
     */
    public static void loadImgByPlaceholder(ImageView imageView,int placeholderId, String url){
        RequestOptions options = new RequestOptions().placeholder(placeholderId);
        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
    }


    public static void loadImgByAll(ImageView imageView,int placeholderId,int errorId, String url){
        RequestOptions options = new RequestOptions().placeholder(placeholderId).error(errorId);
        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
    }




}
