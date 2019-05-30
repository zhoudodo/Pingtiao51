package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.UrlEncoderUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.app.utils.webview.InterceptWebViewClient;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PingtiaoShareRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoShareResponse;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.ShareDialog;
import com.pingtiao51.armsmodule.mvp.ui.helper.JsInterface;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.img.WechatUtils;
import com.pingtiao51.armsmodule.mvp.ui.helper.share.ShareHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.zls.baselib.custom.view.webview.ProgressWebView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class WebViewShareActivity extends BaseWebViewActivity {

    public final static String USER_TYPE = "user_type";
    public final static String NOTE_ID = "note_id";

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


    @Override
    public void setWebClient() {
        ActivityUtils.finishActivity(CreateDianziJietiaoActivity.class);
        initJs2Java();
        getShareInfos();
        progressWebView.setWebViewClient(new InterceptWebViewClient(this) {
        });
    }

    private void runOnUi(boolean visible) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rightTv.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });
    }






    private void initJs2Java() {
        JsInterface jsInterface = new JsInterface(
                SavePreference.getStr(this, PingtiaoConst.KEY_TOKEN),
                this,
                this);

        progressWebView.addJavascriptInterface(jsInterface, "Java2JS");
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

    protected void initPageConfig() {
        super.initPageConfig();
        mTitle = findViewById(R.id.toolbar_title);
        rightTv = findViewById(R.id.right_tv);
        rightTv.setText("发送");
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showShareDialog();
            }
        });
    }

    @Override
    public void loadUrl(final String webviewUrl) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String loadurl = "";
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

    private int mNoteId = 0;
    private int mUserType = 0;
    PingtiaoShareResponse mPingtiaoShareResponse;
    ShareDialog mShareDialog;
    Bitmap mBitmap;

    private void getShareInfos() {
        Intent intent = getIntent();
        mNoteId = intent.getIntExtra(NOTE_ID, 0);
        mUserType = intent.getIntExtra(USER_TYPE, 0);
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PingtiaoApi.class)
                .noteShareInfo(new PingtiaoShareRequest(AppUtils.getAppVersionName(), mNoteId, "ANDRIOD", mUserType))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson<PingtiaoShareResponse>>(ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<PingtiaoShareResponse> pingtiaoShareResponseBaseJson) {
                        if (pingtiaoShareResponseBaseJson.isSuccess()) {
                            mPingtiaoShareResponse = pingtiaoShareResponseBaseJson.getData();
                            String url = UrlDecoderHelper.decode(mPingtiaoShareResponse.getNoteShareWXVO().getPicUrl());
                            Glide.with(WebViewShareActivity.this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    mBitmap = resource;
                                }
                            });
                        }
                    }
                });
    }

    private void showShareDialog() {
        if (mPingtiaoShareResponse == null) {
            return;//空保护
        }
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(WebViewShareActivity.this);
            mShareDialog.setShareListener(new ShareDialog.ShareClickListener() {
                @Override
                public void shareWechat() {
                    WechatUtils.shareWeb(WebViewShareActivity.this,
                            Api.WECHAT_APPKEY,
                            mPingtiaoShareResponse.getNoteShareWXVO().getShareUrl(),
                            mPingtiaoShareResponse.getNoteShareWXVO().getTitle(),
                            mPingtiaoShareResponse.getNoteShareWXVO().getContent(),
                            mBitmap);
                }

                @Override
                public void shareErweima() {

                    Bundle bundle1 = new Bundle();
                    bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "分享二维码");
                    String intentUrl = Api.BASE_H5_URL + "borrowShareCode?userType=" + mUserType + "&id=" + mNoteId;
                    bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, intentUrl);
                    ActivityUtils.startActivity(bundle1, WebViewActivity.class);
                }

                @Override
                public void shareLianjie() {
                    share2Lianjie(mPingtiaoShareResponse.getNoteShareUrlVO().getShareUrl());
                    ArmsUtils.snackbarText("已复制到剪贴板");
                }

                @Override
                public void shareDuanxin() {
                    ShareHelper.sendSms(WebViewShareActivity.this, mPingtiaoShareResponse.getNoteShareSMSVO().getSmsContent());
                }
            });
        }
        mShareDialog.show();
    }

    private void share2Lianjie(String lianjie) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) WebViewShareActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", lianjie);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mShareDialog != null) {
            mShareDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
