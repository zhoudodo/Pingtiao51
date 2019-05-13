package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;

import com.blankj.utilcode.util.AppUtils;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.api.service.PayApi;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.H5PaySuccessTag;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CreateReportRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.CreateDingdanResponse;
import com.pingtiao51.armsmodule.mvp.ui.helper.img.WechatUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class H5PayReportDialog extends H5PayDialog {


    public H5PayReportDialog(Activity context,String ttitleStr,String reportId,double payAmount) {
        super(context);
        this.payAmount = payAmount;
        this.reportId = reportId;
        refreshPayMoney(payAmount);
        title.post(new Runnable() {
            @Override
            public void run() {
            title.setText(ttitleStr);
            }
        });
    }


    private double payAmount;
    private String reportId;

    @Override
    public void createDingdan() {
        String payMethod = "WECHAT";
        String tradeType = "";
        Integer userBankId = null;
        switch (mPayType) {
            case PAY_BANK:
                payMethod = "BANKCARD";
                tradeType = null;
               userBankId = (int) mUserBankListResponse.getId();
                break;
            case PAY_WECHAT:
                payMethod = "WECHAT";
                tradeType = "APP";
                userBankId = null;
                break;
        }
        ArmsUtils.obtainAppComponentFromContext(getContext()).repositoryManager().obtainRetrofitService(PayApi.class)
                .createApplyReportDingdan(new CreateReportRequest(
                        AppUtils.getAppVersionName(),
                         null,
                        "ANDRIOD",
                        payAmount,
                        payMethod,//BANKCARD,WECHAT,ALIPAY
                        reportId,
                        tradeType,
                        userBankId
                ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseJson<CreateDingdanResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseJson<CreateDingdanResponse> rep) {
                        if (rep.isSuccess()) {
                            mCreateDingdanResponse = rep.getData();
                            if (mCreateDingdanResponse == null) {
                                EventBus.getDefault().post(new H5PaySuccessTag());
                                H5PayReportDialog.this.dismiss();
                            } else {
                                switch (mPayType) {
                                    case PAY_WECHAT:
                                        WechatUtils.payWeChat(
                                                getContext(),
                                                Api.WECHAT_APPKEY,
                                                mCreateDingdanResponse.getPartnerId(),
                                                mCreateDingdanResponse.getPrepayId(),
                                                mCreateDingdanResponse.getNonceStr(),
                                                mCreateDingdanResponse.getTimeStamp(),
                                                mCreateDingdanResponse.getPaySign()
                                        );
                                        break;
                                    case PAY_BANK:
                                        payYzm();
                                        break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
