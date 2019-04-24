package com.zls.baselib.custom.view.webview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.SizeUtils;
import com.zls.baselib.R;


/**
 * 进度条 webview
 */
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

    /**
     * 进度条
     */
    private ProgressBar progress_bar_;

    /**
     * 回调
     */
    private OnWebCallBack onweb_callback_;

    /**
     * Description: Default Constructor
     * Created by KAKA on 16/1/19 22:23
     */
    public ProgressWebView(Context context) {
        super(context);
    }

    /**
     * 不能直接调用this(context, attrs,0),最后style是0的话，会导致无法响应点击动作。
     * 但是如果直接把最后一位写成 com.android.internal.R.attr.webViewStyle 编译时会弹出错误提示，原因：
     * You cannot access id's of com.android.internal.R at compile time, but you can access the
     * defined internal resources at runtime and get the resource by name.
     * You should be aware that this is slower than direct access and there is no guarantee.
     */
    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, Resources.getSystem().getIdentifier("webViewStyle", "attr", "android"));
    }

    /**
     * Description: Copy Constructor
     * Created by KAKA on 16/1/19 22:23
     * isInEditMode函数的作用是去除打开XML文档时提示的错误
     */
    public ProgressWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            initView(context);
        }
        setWebViewClient(new ProgressWebViewClient());
        setWebChromeClient(new ProgressWebChromeClient());
    }

    /**
     * Description: 初始化界面元素
     * Created by KAKA on 16/1/20 09:43
     * LayoutParams中第2个参数表示的是progress_bar的高度大小
     */
    private void initView(final Context context) {
        progress_bar_ = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progress_bar_.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, SizeUtils.dp2px(2), 0, 0));
        // 添加drawable
        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_progress_webview);
        progress_bar_.setProgressDrawable(drawable);
        this.addView(progress_bar_);
    }

    /**
     * Description: 设置webview的回调器
     * Created by KAKA on 16/1/20 13:56
     */
    public void setOnWebCallback(OnWebCallBack onwebcallback) {
        this.onweb_callback_ = onwebcallback;
    }

    /**
     * Description: 重写WebViewClient类，不重写的话会跳转默认浏览器
     * Created by KAKA on 16/1/19 23:26
     */
    public class ProgressWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) { // Handle the
            goBack();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /**
         * Description: 获取请求页面的URL
         * Created by KAKA on 16/1/20 13:54
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (onweb_callback_ != null) {
                onweb_callback_.getUrl(url);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
            // super.onReceivedSslError(view, handler, error);
            // 接受所有网站的证书，忽略SSL错误，执行访问网页
            handler.proceed();
        }
    }

    /**
     * Description: 重写webChromeClient类
     * Created by KAKA on 16/1/19 22:40
     */
    public class ProgressWebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progress_bar_.setVisibility(GONE);
            } else {
                if (progress_bar_.getVisibility() == GONE) {
                    progress_bar_.setVisibility(VISIBLE);
                }
                progress_bar_.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        /**
         * Description: 获取页面的标题
         * Created by KAKA on 16/1/20 13:54
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (onweb_callback_ != null) {
                onweb_callback_.getTitle(title);
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progress_bar_.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progress_bar_.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }


    interface OnWebCallBack {
        void getUrl(String url);

        void getTitle(String title);
    }

    /**
     * 禁用网页上的点击事件
     */
    public void setNoClick() {
        setClickable(false);
        setLongClickable(false);
        setFocusable(false);
        setFocusableInTouchMode(false);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
    }
}
