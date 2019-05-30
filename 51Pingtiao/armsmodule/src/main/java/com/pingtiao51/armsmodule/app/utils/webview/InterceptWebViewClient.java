package com.pingtiao51.armsmodule.app.utils.webview;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.ActivityUtils;

public class InterceptWebViewClient extends WebViewClient {

    ShouldInterceptRequestCallback mShouldInterceptRequestCallback;

    public InterceptWebViewClient(ShouldInterceptRequestCallback callback) {
        mShouldInterceptRequestCallback = callback;
    }


    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (mShouldInterceptRequestCallback != null) {
            mShouldInterceptRequestCallback.interceptRequestUrl(url);
        }
        return super.shouldInterceptRequest(view, url);
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
// 如下方案可在非微信内部WebView的H5页面中调出微信支付
        if (url.startsWith("weixin://wap/pay?")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            ActivityUtils.startActivity(intent);
            return true;

        }

        return super.shouldOverrideUrlLoading(view, url);

    }
}
