package com.receipt.px.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.PaySuccessTag;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends FragmentActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry_layout);
        api = WXAPIFactory.createWXAPI(this, Api.WECHAT_APPKEY);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
//        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                ArmsUtils.snackbarText("支付成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ArmsUtils.snackbarText("支付取消");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ArmsUtils.snackbarText("支付取消");
                break;
            default:
                ArmsUtils.snackbarText("支付失败");
                break;
        }
        EventBus.getDefault().post(new PaySuccessTag(baseResp.errCode));
        this.finish();
    }
}
