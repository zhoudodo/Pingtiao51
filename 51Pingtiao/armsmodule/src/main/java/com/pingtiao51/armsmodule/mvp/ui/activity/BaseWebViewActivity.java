package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.UrlEncoderUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.app.utils.webview.ShouldInterceptRequestCallback;
import com.pingtiao51.armsmodule.mvp.ui.helper.JsInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.zls.baselib.custom.view.webview.ProgressWebView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_DCIM;

public abstract class BaseWebViewActivity extends FragmentActivity implements JsInterface.Js2JavaInterface,ShouldInterceptRequestCallback {
    public final static String WEBVIEW_URL = "WEBVIEW_URL";
    public final static String WEBVIEW_TITLE = "WEBVIEW_TITLE";
    private Intent recIntent;
    ProgressWebView progressWebView;
    public final static String WEBVIEW_OTHERS = "webview_others";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setRootView());
        recIntent = getIntent();
        initPageInfos();
        initWebViews();
        initPageConfig();
    }


    public abstract int setRootView();

    public abstract ProgressWebView setProgressWebView();

    public abstract void setWebClient();

    private void initPageInfos() {
        String title = recIntent.getStringExtra(WEBVIEW_TITLE);
        setTitle(title);
        String webviewUrl = recIntent.getStringExtra(WEBVIEW_URL);
        progressWebView = setProgressWebView();
        if (UrlEncoderUtils.hasUrlEncoded(webviewUrl)) {
            try {
                webviewUrl = URLDecoder.decode(webviewUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        progressWebView.loadUrl(webviewUrl);
        progressWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final WebView.HitTestResult hitTestResult = progressWebView.getHitTestResult();
                // 如果是图片类型或者是带有图片链接的类型
                if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                        hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    // 弹出保存图片的对话框
                    new AlertDialog.Builder(BaseWebViewActivity.this)
                            .setItems(new String[]{"保存图片到本地"
//                                    , "分享图片"
                            }, (dialog, which) -> {
                                String pic = hitTestResult.getExtra();//获取图片
                                switch (which) {
                                    case 0:
                                        //保存图片到相册
                                        saveImage(pic);
                                        break;
                                    case 1:
                                        // 分享图片，这里用RxJava处理异步

                                        break;
                                }
                            })
                            .show();
                    return true;
                }
                return false;
            }
        });


    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViews() {
        WebSettings settings = progressWebView.getSettings();
        //本APP需要
//        settings.setUserAgentString(settings.getUserAgentString());
        String userAgent = settings.getUserAgentString();
        settings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(false);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= 8) {
            settings.setPluginState(WebSettings.PluginState.ON);
        } else {
            //settings.setPluginState(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
        } else {
            try {
                Class<?> webview = Class.forName("android.webkit.WebView");
                Method method = webview.getMethod("getZoomButtonsController");
                method.invoke(this, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        progressWebView.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Log.d("dodo","url = " + url);
                        return super.shouldOverrideUrlLoading(view, url);
                    }

                    @Override
                    public void onLoadResource(WebView view, String url) {
                        Log.d("dodo","url2 = " + url);
                        super.onLoadResource(view, url);
                    }
                }
        );
        setWebClient();
    }

    @Override
    public void onBackPressed() {
        if (progressWebView.canGoBack()) {
            progressWebView.goBack(); //goBack()表示返回webView的上一页面
            return;
        } else {
            super.onBackPressed();
        }
    }

    public boolean onKeyDown(int keyCoder, KeyEvent event) {
        if (progressWebView.canGoBack() && keyCoder == KeyEvent.KEYCODE_BACK) {
            progressWebView.goBack(); //goBack()表示返回webView的上一页面
            return true;
        } else if (keyCoder == KeyEvent.KEYCODE_BACK) {
            finish();// 按了返回键，但已经不能返回，则执行退出确认
            return true;
        }
        return super.onKeyDown(keyCoder, event);
    }

    protected void onDestroy() {
        if (progressWebView != null) {
            progressWebView.setVisibility(View.GONE);
            progressWebView.removeAllViews();
            progressWebView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (webView != null) {
//            //暂停WebView在后台的所有活动
//            webView.onPause();
//            //暂停WebView在后台的JS活动
//            webView.pauseTimers();
//        }
    }

    private int mCount = 0;

    @Override
    protected void onResume() {
        super.onResume();
        String json = mCount + "";
        mCount++;
        if (!TextUtils.isEmpty(SavePreference.getStr(this, PingtiaoConst.KEY_TOKEN)) && getIntent().getBooleanExtra(WEBVIEW_OTHERS, true)) {
            progressWebView.loadUrl("javascript:reloadPage(" + "'" + json + "'" + ")");
        }
//        progressWebView.loadUrl("javascript:reloadPage()");
    }

    public void saveImage(String data) {
        try {
            Glide.with(BaseWebViewActivity.this).
                    asBitmap().load(data).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Bitmap bitmap = resource;
                    if (bitmap != null) {
                        save2Album(bitmap, new SimpleDateFormat("SXS_yyyyMMddHHmmss", Locale.getDefault()).format(new Date()) + ".jpg");
                    } else {
                        runOnUiThread(() -> Toast.makeText(BaseWebViewActivity.this, "保存失败", Toast.LENGTH_SHORT).show());
                    }
                }
            });
        } catch (Exception e) {
            runOnUiThread(() -> Toast.makeText(BaseWebViewActivity.this, "保存失败", Toast.LENGTH_SHORT).show());
            e.printStackTrace();
        }
    }


    private void save2Album(Bitmap bitmap, String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM), fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            runOnUiThread(() -> {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                ArmsUtils.snackbarText("保存成功");
            });
        } catch (Exception e) {
            runOnUiThread(() -> ArmsUtils.snackbarText("保存失败"));
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void setTitle(String str) {

    }


    @Override
    public void showDialog() {

    }

    @Override
    public void loadUrl(String url) {

    }

    @Override
    public void reloadUrl(String url) {

    }


    protected TextView rightTv;
    protected TextView mTitle;

    protected void initPageConfig() {
        mTitle = findViewById(R.id.toolbar_title);
        rightTv = findViewById(R.id.right_tv);
        rightTv.setText("");
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void setRightTitle(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (rightTv != null) {
                    if (TextUtils.isEmpty(str)) {
                        rightTv.setVisibility(View.GONE);
                    } else {
                        rightTv.setVisibility(View.VISIBLE);
                        rightTv.setText(str);
                    }
                }
            }
        });
    }

    @Override
    public void setRightClick(String str, String jscode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (rightTv != null) {
                    if (TextUtils.isEmpty(str)) {
                        rightTv.setVisibility(View.GONE);
                    } else {
                        rightTv.setVisibility(View.VISIBLE);
                        rightTv.setText(str);
                    }
                    rightTv.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(View v) {
                            if (progressWebView != null) {
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                    // 如果当前版本小于 JELLY_BEAN_MR2 版本，即4.4版本
                                    progressWebView.loadUrl(jscode);
                                } else {
                                    //因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
                                    progressWebView.evaluateJavascript(jscode, new ValueCallback<String>() {
                                        @Override
                                        public void onReceiveValue(String value) {

                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    //监听到Url发生变化就隐藏右边标题
    @Override
    public void interceptRequestUrl(String url){
        //由于时序问题暂时搁置
    }
}
