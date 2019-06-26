package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.model.api.service.UserService;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.LoginEventTag;
import com.pingtiao51.armsmodule.mvp.model.entity.request.SendCodeRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.WxBindPhoneRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.WxBindPhoneResponse;
import com.pingtiao51.armsmodule.mvp.ui.broadcast.jpush.JpushManager;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.InputLoginView;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class BindPhoneNumActivity extends FragmentActivity {


    public final static String WXCODE = "VCODE";

    InputLoginView bind_phone_input_view;
    TextView bind_phone_ok;

    private String wechatCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phonenum);
        setTitle("绑定手机号");
        bind_phone_input_view = findViewById(R.id.bind_phone_input_view);
        bind_phone_ok = findViewById(R.id.bind_phone_ok);
        bind_phone_input_view.setTypeView(InputLoginView.CODE_LOGIN);
        wechatCode = getIntent().getStringExtra(WXCODE);
        initEvents();
    }

    private void initEvents() {
        bind_phone_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind_phone_input_view.loginWithType();
            }
        });
        bind_phone_input_view.registerInterface(new InputLoginView.InputLoginViewInterface() {
            @Override
            public void sendCode(String phoneNumber, String type) {
                BindPhoneNumActivity.this.sendCode(phoneNumber, type);
            }

            @Override
            public void loginCode(boolean b, String psw, long phoneNum) {
                //这个psw其实是vcode
                BindPhoneNumActivity.this.binePhone(psw, phoneNum);
            }

            @Override
            public void updatePsw(long phoneNum, String authCode, String psw) {

            }
        });
    }

    private void sendCode(String phoneNumber, String type) {
        String codeType = "EASY_AUTH";
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(UserService.class)
                .sendCodeRequest(new SendCodeRequest(AppUtils.getAppVersionName(), "ANDRIOD", phoneNumber, codeType))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<Object> objectBaseJson) {
                        if (objectBaseJson.isSuccess()) {
                            ArmsUtils.snackbarText("短信验证码发送成功");
                        } else {
                            ArmsUtils.snackbarText(objectBaseJson.getMessage());
                        }
                    }
                });
    }


    @SuppressLint("MissingPermission")
    private void binePhone(String code, long phoneNum) {
        ArmsUtils.obtainAppComponentFromContext(this).repositoryManager().obtainRetrofitService(UserService.class)
                .wxLoginBindingPhone(new WxBindPhoneRequest(
                        AppUtils.getAppVersionName(),
                        wechatCode,
                        PhoneUtils.getDeviceId(),
                        null,
                        null,
                        "ANDRIOD",
                        String.valueOf(phoneNum),
                        null,
                        "ANDROID_YINGYONGBAO",
                        code
                ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson<WxBindPhoneResponse>>(ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler()) {
                    @Override
                    public void onNext(BaseJson<WxBindPhoneResponse> objectBaseJson) {
                        if (objectBaseJson.isSuccess()) {
                            ArmsUtils.snackbarText("绑定成功！");
                            WxBindPhoneResponse response = objectBaseJson.getData();
                            SavePreference.save(PingtiaoConst.KEY_TOKEN, response.getTokenVO().getToken());
                            EventBus.getDefault().post(new LoginEventTag());
                            JpushManager.setAlias(response.getPhone());
                            ActivityUtils.finishActivity(LoginActivity.class);
                            finish();
                        } else {
                            ArmsUtils.snackbarText(objectBaseJson.getMessage());
                        }
                    }
                });
    }
}
