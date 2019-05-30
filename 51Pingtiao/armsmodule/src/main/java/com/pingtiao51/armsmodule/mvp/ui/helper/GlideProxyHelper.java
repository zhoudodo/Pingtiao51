package com.pingtiao51.armsmodule.mvp.ui.helper;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Glide代理 by yuchaoliang on 16/4/19.
 */
public class GlideProxyHelper {

    /**
     * 加载本地图片
     *
     * @param mImageView
     * @param ids
     */
    public static void loadImgForRes(ImageView mImageView, int ids) {
        Glide.with(mImageView.getContext()).load(ids).into(mImageView);
    }


    /**
     * 加载网络图片
     *
     * @param mImageView
     * @param url
     */
    public static void loadImgForUrl(ImageView mImageView, String url) {
        RequestOptions options = new RequestOptions()
                .dontAnimate();
        Glide.with(mImageView
                .getContext())
                .load(url)
                .apply(options)
                .into(mImageView);
    }

    public static void loadImgForLocal(ImageView mImageView, String url) {
        RequestOptions options = new RequestOptions()
                .dontAnimate();
        Glide.with(mImageView
                .getContext())
                .load(url)
                .apply(options)
                .into(mImageView);
    }


    /**
     * 占位图  网络图片加载
     *
     * @param imageView
     * @param placeholderId
     * @param url
     */
    public static void loadImgByPlaceholder(ImageView imageView, int placeholderId, String url) {
        RequestOptions options = new RequestOptions().placeholder(placeholderId);
        if(TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
        }else {
            Glide.with(imageView.getContext()).load(new GlideUrlHelper(url)).apply(options).into(imageView);
        }
    }


    public static void loadImgByAll(ImageView imageView, int placeholderId, int errorId, String url) {
        RequestOptions options = new RequestOptions().placeholder(placeholderId).error(errorId);
        if(TextUtils.isEmpty(url)){
            Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
        }else {
            Glide.with(imageView.getContext()).load(new GlideUrlHelper(url)).apply(options).into(imageView);
        }
    }




}
