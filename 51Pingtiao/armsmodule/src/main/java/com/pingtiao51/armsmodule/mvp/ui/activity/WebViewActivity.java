package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.utils.UrlEncoderUtils;
import com.pingtiao51.armsmodule.BuildConfig;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.app.utils.webview.InterceptWebViewClient;
import com.pingtiao51.armsmodule.mvp.ui.helper.JsInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.zls.baselib.custom.view.webview.ProgressWebView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.OnClick;

public class WebViewActivity extends BaseWebViewActivity {

    public final static String USER_TYPE = "user_type";

    @Override
    public int setRootView() {
        return R.layout.activity_webview_layout;
    }

    @OnClick({R.id.load_url_btn})
    public void onPageClick(View view){
        switch (view.getId()){
            case R.id.load_url_btn:
                EditText tv = findViewById(R.id.url_et);
                progressWebView.loadUrl(tv.getText().toString());
                break;
        }

    }

    @Override
    public ProgressWebView setProgressWebView() {
        return findViewById(R.id.progress_webview);
    }

    TextView mTitle;
    @Override
    public void setWebClient() {
        findViewById(R.id.linear).setVisibility(BuildConfig.DEBUG?View.VISIBLE:View.GONE);
        mTitle = findViewById(R.id.toolbar_title);
        JsInterface jsInterface = new JsInterface(
                SavePreference.getStr(this, PingtiaoConst.KEY_TOKEN),
                this,
                this
                );

        progressWebView.addJavascriptInterface(jsInterface,"Java2JS");
        progressWebView.setWebViewClient(new InterceptWebViewClient(this){

        });
    }


    @Override
    public void setTitle(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTitle != null) {
                    mTitle.setText(str);
                }
            }
        });
    }

    @Override
    public void setRightTitle(String str) {
        super.setRightTitle(str);
    }
    @Override
    public void setRightClick(String str,String jscode) {
        super.setRightClick(str,jscode);
    }




    @Override
    public void showDialog() {

    }

    @Override
    public void loadUrl(final String webviewUrl) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String loadurl = webviewUrl;
                progressWebView = setProgressWebView();
                if (UrlEncoderUtils.hasUrlEncoded(webviewUrl)) {
                    try {
                        loadurl = URLDecoder.decode(webviewUrl, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                progressWebView.loadUrl(loadurl);
            }
        });

    }

    @Override
    public void reloadUrl(final String webviewUrl) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String loadurl = webviewUrl;
                progressWebView = setProgressWebView();
                if (UrlEncoderUtils.hasUrlEncoded(webviewUrl)) {
                    try {
                        loadurl = URLDecoder.decode(webviewUrl, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                progressWebView.loadUrl(loadurl);
            }
        });

    }
}
