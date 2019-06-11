package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.jess.arms.utils.UrlEncoderUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.app.utils.webview.InterceptWebViewClient;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CloseElectronicNoteRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.ui.helper.JsInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.zls.baselib.custom.view.dialog.DialogChooseNormal;
import com.zls.baselib.custom.view.webview.ProgressWebView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * 签章Web页面
 */
public class WebViewSignActivity extends BaseWebViewActivity {

    public final static String XIE_JIETIAO_RETURNURL = Api.BASE_H5_URL + "xiejietiao_returnurl";
    public final static String XIE_SHOUTIAO_RETURNURL = Api.BASE_H5_URL + "xieshoutiao_returnurl";

    public static String NOTE_ID = "note_id";
    public static String USER_TYPE = "userType";

    @Override
    public int setRootView() {
        return R.layout.activity_webview_layout;
    }

    @OnClick({R.id.load_url_btn})
    public void onPageClick(View view) {
        switch (view.getId()) {
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
                this
        );

        progressWebView.addJavascriptInterface(jsInterface, "Java2JS");
        progressWebView.setWebViewClient(new InterceptWebViewClient(this) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //后台重定向网页 http://%s.51pingtiao.com/borrowShare?id=%s&userType=0&from=list
                if (url.contains(XIE_JIETIAO_RETURNURL)) {
                    //跳转至H5
                    h5SharePage();
                    return true;
                } else if (url.contains(XIE_SHOUTIAO_RETURNURL)) {
                    toPingtiaoList();
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
        });
    }


    DialogChooseNormal mDialogChooseNormal;

    @Override
    public void onBackPressed() {
        if (progressWebView.canGoBack()) {
            progressWebView.goBack(); //goBack()表示返回webView的上一页面
            return;
        } else {
//            super.onBackPressed();
            String noteid = getIntent().getStringExtra(NOTE_ID);
            if (mDialogChooseNormal == null) {
                mDialogChooseNormal = new DialogChooseNormal.ChooseBuilder()
                        .setTitle("提示")
                        .setContext(ActivityUtils.getTopActivity())
                        .setContent("确定放弃签署本借条？\n" +
                                "放弃签署的借条将予以销毁，不可恢复。")
                        .setBtn1Content("放弃签署").setOnClickListener1(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDialogChooseNormal.dismiss();
                                ArmsUtils.obtainAppComponentFromContext(WebViewSignActivity.this).repositoryManager().obtainRetrofitService(PingtiaoApi.class)
                                        .closeElectronicNote(new CloseElectronicNoteRequest(
                                                AppUtils.getAppVersionName(),
                                                Long.valueOf(noteid),
                                                "ANDRIOD",
                                                1L
                                        )).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(ArmsUtils.obtainAppComponentFromContext(WebViewSignActivity.this).rxErrorHandler()) {
                                            @Override
                                            public void onNext(BaseJson<Object> rep) {
                                                if (rep.isSuccess()) {
                                                    finish();
                                                } else {
                                                    ArmsUtils.snackbarText(rep.getMessage());
                                                }
                                            }
                                        });

                            }
                        })
                        .setBtn1Colort(R.color.gray_color_7D7D7D)
                        .setBtn3Content("继续签署")
                        .setOnClickListener3(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDialogChooseNormal.dismiss();
                            }
                        }).build();
            }
            mDialogChooseNormal.show();
        }
    }


    private void h5SharePage() {
        Intent i = getIntent();
        int mType = i.getIntExtra(USER_TYPE, 0);
        String mNoteid = i.getStringExtra(NOTE_ID);
        Bundle bundle1 = new Bundle();
        bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "分享");
        bundle1.putInt(WebViewShareActivity.USER_TYPE, mType);
        bundle1.putInt(WebViewShareActivity.NOTE_ID, Integer.valueOf(mNoteid));
        bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + "borrowShare?id=" + mNoteid + "&userType=" + mType);
        ActivityUtils.startActivity(bundle1, WebViewShareActivity.class);
        finish();
    }

    /**
     * 去凭条列表界面
     */
    private void toPingtiaoList() {
        Bundle bundleX = new Bundle();
        bundleX.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI_SHOU);
        bundleX.putInt(MyPingtiaoActivity.FINISH_CREATE, MyPingtiaoActivity.BACK_FINISH_CREATE);
        ActivityUtils.startActivity(bundleX, MyPingtiaoActivity.class);
        ActivityUtils.finishActivity(CreateDianziJietiaoActivity.class);
        ActivityUtils.finishActivity(CreateJietiaoActivity.class);
        finish();
    }


    @Override
    public void setTitle(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTitle != null) {
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
    public void setRightClick(String typeName, String str, String jscode) {
        super.setRightClick(typeName, str, jscode);
    }


    @Override
    public void showDialog() {

    }

    @Override
    public void loadUrl(String webviewUrl) {
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
    public void reloadUrl(String webviewUrl) {
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
