package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.pingtiao51.armsmodule.BuildConfig;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.helper.JsInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.zls.baselib.custom.view.webview.ProgressWebView;

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
//        findViewById(R.id.linear).setVisibility(BuildConfig.DEBUG?View.VISIBLE:View.GONE);
        mTitle = findViewById(R.id.toolbar_title);
        JsInterface jsInterface = new JsInterface(
                SavePreference.getStr(this, PingtiaoConst.KEY_TOKEN),
                this,
                new JsInterface.Js2JavaInterface() {
                    @Override
                    public void setTitle(String str) {
                        mTitle.post(new Runnable() {
                            @Override
                            public void run() {
                                mTitle.setText(str);
                            }
                        });

                    }

                    @Override
                    public void setRightTitle(String str) {

                    }

                    @Override
                    public void showDialog() {

                    }
                });

        progressWebView.addJavascriptInterface(jsInterface,"Java2JS");
        progressWebView.setWebViewClient(new WebViewClient());
    }
}
