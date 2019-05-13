package com.receipt.px.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXEntryActivity extends FragmentActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry_layout);
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
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                    switch (baseResp.getType()){
                        // ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX是微信分享，api自带
                        case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                            //微信分享成功
                            ArmsUtils.snackbarText("分享成功");
                            break;
                        default:
                            break;
                    }
                    break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                switch (baseResp.getType()){
                    // ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX是微信分享，api自带
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //微信分享成功
                        ArmsUtils.snackbarText("分享取消");
                        break;
                    default:
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                switch (baseResp.getType()){
                    // ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX是微信分享，api自带
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //微信分享成功
                        ArmsUtils.snackbarText("分享取消");
                        break;
                    default:
                        break;
                }
                break;
            default:
                switch (baseResp.getType()){
                    // ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX是微信分享，api自带
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //微信分享成功
                        ArmsUtils.snackbarText("分享失败");
                        finish();
                        break;
                    default:
                        break;
                }
                break;
        }
        this.finish();
    }



}
