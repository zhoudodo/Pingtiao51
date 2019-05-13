package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.integration.lifecycle.Lifecycleable;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.EditRepaymentRecordRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PingtiaoShareRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.RepaymentTipToLenderRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoShareResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.RepaymentTipToLenderResponse;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.ShareDialog;
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper;
import com.pingtiao51.armsmodule.mvp.ui.helper.img.WechatUtils;
import com.pingtiao51.armsmodule.mvp.ui.helper.share.ShareHelper;
import com.zls.baselib.custom.view.dialog.DialogChooseNormal;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity.PING_TIAO_XIANG_QING;

/**
 * 还款状态 页面
 */
public class HuankuanStatusActivity extends FragmentActivity implements View.OnClickListener {
    public final static String USER_TYPE = "user_type";
    public final static String NOTE_ID = "note_id";

    public final static String BORROW = "borrow";
    public final static String LENDER = "lender";
    public final static String AMOUNT = "amount";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huankuan_status);
        initViews();
    }

    TextView chexiao_btn;
    TextView hint_other;

    private void initViews() {

        borrow = getIntent().getStringExtra(BORROW);
        lender = getIntent().getStringExtra(LENDER);
        amount = getIntent().getDoubleExtra(AMOUNT, 0);
        getShareInfos();
        chexiao_btn = findViewById(R.id.chexiao_btn);
        chexiao_btn.setOnClickListener(this);
        hint_other = findViewById(R.id.hint_other);
        hint_other.setOnClickListener(this);
        setTitle("还款状态");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chexiao_btn:
                //TODO 撤销
                chexiao();

                break;
            case R.id.hint_other:
                //todo 提醒对方
                if (mShareDialog == null) {
                    mShareDialog = new ShareDialog(this, "提醒方式", new int[]{1, 0, 1, 0});
                    mShareDialog.setShareListener(new ShareDialog.ShareClickListener() {
                        @Override
                        public void shareWechat() {
                            mShareDialog.dismiss();
                            WechatUtils.shareWeb(HuankuanStatusActivity.this,
                                    Api.WECHAT_APPKEY,
                                    mPingtiaoShareResponse.getNoteShareWXVO().getShareUrl(),
                                    mPingtiaoShareResponse.getNoteShareWXVO().getTitle(),
                                    mPingtiaoShareResponse.getNoteShareWXVO().getContent(),
                                    mBitmap);
                        }

                        @Override
                        public void shareErweima() {
                            mShareDialog.dismiss();
                            Bundle bundle1 = new Bundle();
                            bundle1.putString(BaseWebViewActivity.WEBVIEW_TITLE, "分享二维码");
                            String intentUrl = Api.BASE_H5_URL + "borrowShareCode?userType=" + mUserType + "&id=" + mNoteId;
                            bundle1.putString(BaseWebViewActivity.WEBVIEW_URL, intentUrl);
                            ActivityUtils.startActivity(bundle1, WebViewActivity.class);
                        }

                        @Override
                        public void shareLianjie() {
                            mShareDialog.dismiss();
                            share2Lianjie(mPingtiaoShareResponse.getNoteShareUrlVO().getShareUrl());
                            ArmsUtils.snackbarText("已复制到剪贴板");
                        }

                        @Override
                        public void shareDuanxin() {
                            mShareDialog.dismiss();
                            ShareHelper.sendSms(HuankuanStatusActivity.this, mPingtiaoShareResponse.getNoteShareSMSVO().getSmsContent());
                        }
                    });
                }
                mShareDialog.show();
                break;
        }
    }

    private void share2Lianjie(String lianjie) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) HuankuanStatusActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", lianjie);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }


    private int mNoteId = 0;
    private int mUserType = 0;
    RepaymentTipToLenderResponse mPingtiaoShareResponse;
    ShareDialog mShareDialog;
    Bitmap mBitmap;

    private void getShareInfos() {
        Intent intent = getIntent();
        mNoteId = intent.getIntExtra(NOTE_ID, 0);
        mUserType = intent.getIntExtra(USER_TYPE, 0);
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PingtiaoApi.class)
                .repaymentTipToLender(new RepaymentTipToLenderRequest(AppUtils.getAppVersionName(), mNoteId, "ANDRIOD"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson<RepaymentTipToLenderResponse>>(ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<RepaymentTipToLenderResponse> repaymentTipToLenderResponseBaseJson) {
                        if (repaymentTipToLenderResponseBaseJson.isSuccess()) {
                            mPingtiaoShareResponse = repaymentTipToLenderResponseBaseJson.getData();
                            String url = UrlDecoderHelper.decode(mPingtiaoShareResponse.getNoteShareWXVO().getPicUrl());
                            Glide.with(HuankuanStatusActivity.this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    mBitmap = resource;
                                }
                            });
                        }
                    }
                });
    }

    DialogChooseNormal mDialogChooseNormal;

    private void chexiao() {
        if (mDialogChooseNormal == null) {
            mDialogChooseNormal = new DialogChooseNormal.ChooseBuilder()
                    .setTitle("提示")
                    .setContext(this)
                    .setContent("确认撤销还款通知?")
                    .setBtn1Content("暂不撤销").setOnClickListener1(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDialogChooseNormal.dismiss();
                        }
                    })
                    .setBtn1Colort(R.color.gray_color_7D7D7D)
                    .setBtn3Content("确认撤销")
                    .setOnClickListener3(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            chexiaoReq();
                        }
                    }).build();
        }
        mDialogChooseNormal.show();
    }

    private String sendReason = "";
    private String borrow = "";
    private String lender = "";
    private double amount;
    private String operationType = "REVOKE";//撤销 REVOKE 确认 CONFIRM 驳回REJECT 销账CANCLE

    private void chexiaoReq() {
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(PingtiaoApi.class).editRepaymentRecord(
                new EditRepaymentRecordRequest(
                        amount,
                        AppUtils.getAppVersionName(),
//                        sendReason,
                        null,
                        mNoteId,
                        operationType,
                        "ANDRIOD"
//                        lender,
//                        borrow
                )
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<Object> rep) {
                        if (rep.isSuccess()) {
                            //成功销账
                            if (mDialogChooseNormal != null) {
                                mDialogChooseNormal.dismiss();
                            }
                            ArmsUtils.snackbarText("成功撤销");
//                            toHuankuanstatus();
                            startToXiangqing();
                            finish();
                        } else {
                            ArmsUtils.snackbarText(rep.getMessage());
                        }
                    }
                });
    }


    private void toHuankuanstatus() {
        Bundle bundle1 = new Bundle();
        bundle1.putString(HuankuanStatusActivity.BORROW, borrow);
        bundle1.putString(HuankuanStatusActivity.LENDER, lender);
        bundle1.putDouble(HuankuanStatusActivity.AMOUNT, amount);
        bundle1.putInt(HuankuanStatusActivity.NOTE_ID, mNoteId);
        bundle1.putInt(HuankuanStatusActivity.USER_TYPE, mUserType);
        ActivityUtils.startActivity(bundle1, HuankuanStatusActivity.class);
    }

    //跳转至详情界面
    private void startToXiangqing() {
        ActivityUtils.finishActivity(DianziJietiaoXiangqingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(PING_TIAO_XIANG_QING, mNoteId);
        ActivityUtils.startActivity(bundle, DianziJietiaoXiangqingActivity.class);
    }
}
