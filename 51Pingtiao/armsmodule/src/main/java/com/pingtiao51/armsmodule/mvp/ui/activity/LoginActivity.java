package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.BuildConfig;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.di.component.DaggerLoginComponent;
import com.pingtiao51.armsmodule.mvp.contract.LoginContract;
import com.pingtiao51.armsmodule.mvp.model.api.Api;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.LoginEventTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.UnLoginBackTag;
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.WechatLoginTag;
import com.pingtiao51.armsmodule.mvp.model.entity.response.LoginResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.WxLoginResponse;
import com.pingtiao51.armsmodule.mvp.presenter.LoginPresenter;
import com.pingtiao51.armsmodule.mvp.ui.broadcast.jpush.JpushManager;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.InputLoginView;
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.pingtiao51.armsmodule.mvp.ui.helper.wechat.WechatUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 10:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseArmsActivity<LoginPresenter> implements LoginContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @BindView(R.id.input_view)
    InputLoginView loginView;

    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.login_btn)
    TextView login_btn;
    @BindView(R.id.service_hint)
    TextView service_hint;

    @BindView(R.id.service_hint2)
    TextView service_hint2;

    @BindView(R.id.login_psd)
    TextView login_psd;
    @BindView(R.id.forget_psd)
    TextView forget_psd;
    @BindView(R.id.login_code)
    TextView login_code;

    @BindView(R.id.other_login_rl)
    RelativeLayout other_login_rl;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.back_btn, R.id.login_btn, R.id.login_psd, R.id.forget_psd, R.id.login_code, R.id.welcome_hints
    ,R.id.wx_login})
    public void onPageClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                //返回上个页面 回退规则
                switch (mMode) {
                    case InputLoginView.CHANGE_PSD:
                        finish();

                        break;
                    default:
                        switch (loginView.getViewType()) {
                            case InputLoginView.CHANGE_PSD:
                                MobclickAgent.onEvent(this, "yanzhengmafanhui", "登录页\t点击“验证码登录”的“返回”");
                                loginPsw();
                                break;
                            case InputLoginView.PSD_LOGIN:
                                loginCode();
                                break;
                            case InputLoginView.CODE_LOGIN:
                                onBackPressed();
                                break;
                        }
                        break;
                }
                break;
            case R.id.login_btn:
                //登录按钮
                loginView.loginWithType();
                break;
            case R.id.login_psd:
                //密码登录
                MobclickAgent.onEvent(this, "code_wangjimima", "登录页\t点击“忘记密码”");
                loginPsw();
                break;
            case R.id.forget_psd:
                forgetPsw();
                //忘记密码
                break;
            case R.id.login_code:
                //短信验证码登录
                loginCode();
                break;
            case R.id.welcome_hints:
                if(BuildConfig.DEBUG) {

                }
                break;
            case R.id.wx_login:
                WechatUtils.loginWx(this,Api.WECHAT_APPKEY);
//                BiometricPromptHelper.Companion.showBiometric(this,"你好","指纹识别","取消");
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new UnLoginBackTag());
        for (Activity act: ActivityUtils.getActivityList()) {
            if(!(act instanceof MainActivity)){
                act.finish();
            }
        }
    }

    int count = 0;
    /**
     * 验证码登录
     */
    private void loginCode() {
        //短信验证码登录
        loginView.setTypeView(InputLoginView.CODE_LOGIN);
        login_code.setVisibility(View.GONE);
        forget_psd.setVisibility(View.GONE);
        login_psd.setVisibility(View.VISIBLE);
        service_hint.setVisibility(View.VISIBLE);
        service_hint2.setVisibility(View.VISIBLE);
        other_login_rl.setVisibility(View.VISIBLE);
//        SpannableStringBuilder builder =  new SpanUtils()
//                .append("*温馨提醒：未注册51凭条账号的手机号，登录时将自动注\n" +
//                        "册，且代表您已同意").setForegroundColor(getResources().getColor(R.color.gray_color_A4A4A4))
//                .appendLine("《用户注册协议》")
//                .setForegroundColor(getResources().getColor(R.color.orange_color_FFA135))
//                .create();
        SpannableStringBuilder builder =  new SpanUtils()
                .append("登陆即代表您已同意").setForegroundColor(getResources().getColor(R.color.gray_color_A4A4A4))
                .appendLine("《用户注册协议》")
                .setForegroundColor(getResources().getColor(R.color.orange_color_FFA135))
                .create();
        service_hint.setText(builder);
        service_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 用户协议 跳转
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebViewActivity.WEBVIEW_TITLE, "用户注册协议");
                bundle.putString(BaseWebViewActivity.WEBVIEW_URL, Api.BASE_H5_URL + Api.REG_AGREEMENTOK);
                startActBundle(bundle, WebViewActivity.class);
            }
        });

        SpannableStringBuilder builder2 =  new SpanUtils()
                .append("未注册账号的手机号，登录时将自动注册").setForegroundColor(getResources().getColor(R.color.gray_color_B3B3B3))
                .create();
        service_hint2.setText(builder2);

//        loginView.setmLoginPresenter(mPresenter);
    }

    private void loginPsw() {
        //密码登录
        loginView.setTypeView(InputLoginView.PSD_LOGIN);
        login_code.setVisibility(View.VISIBLE);
        forget_psd.setVisibility(View.VISIBLE);
        login_psd.setVisibility(View.GONE);
        service_hint.setVisibility(View.GONE);
        service_hint2.setVisibility(View.GONE);
        other_login_rl.setVisibility(View.GONE);
    }

    private void forgetPsw() {
        loginView.setTypeView(InputLoginView.CHANGE_PSD);
        login_code.setVisibility(View.GONE);
        forget_psd.setVisibility(View.GONE);
        login_psd.setVisibility(View.GONE);
        service_hint.setVisibility(View.GONE);
        service_hint2.setVisibility(View.GONE);
        other_login_rl.setVisibility(View.GONE);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public final static String LOGIN_MODE = "login_mode";
    private int mMode = InputLoginView.CODE_LOGIN;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        SavePreference.save(PingtiaoConst.KEY_TOKEN,"");
        mMode = getIntent().getIntExtra(LOGIN_MODE, InputLoginView.CODE_LOGIN);
        switch (mMode) {
            case InputLoginView.CHANGE_PSD:
                forgetPsw();
                break;
            case InputLoginView.CODE_LOGIN:
                loginCode();
                break;
            case InputLoginView.PSD_LOGIN:
                loginPsw();
                break;
        }

        loginView.registerInterface(new InputLoginView.InputLoginViewInterface() {
            @Override
            public void sendCode(String phoneNumber, String type) {
                mPresenter.sendCode(phoneNumber,type);
            }

            @Override
            public void loginCode(boolean b, String psw, long phoneNum) {
                mPresenter.loginCode(b,psw,phoneNum);
            }

            @Override
            public void updatePsw(long phoneNum, String authCode, String psw) {
                mPresenter.updatePsw(phoneNum, authCode, psw);
            }
        });

    }


    @Override
    public void sendCodeSuc(Object object) {
        //短信验证码发送成功
        ArmsUtils.snackbarText("短信验证码发送成功");
    }

    @Override
    public void loginSuc(LoginResponse loginResponse, String phoneNum) {
        SavePreference.save(PingtiaoConst.KEY_TOKEN, loginResponse.getToken());
        EventBus.getDefault().post(new LoginEventTag());
        Log.d("loginSuc",phoneNum);
        JpushManager.setAlias(phoneNum);
        finish();
    }


    @Override
    public void changePswSuc(Object object) {
        //忘记密码 界面成功返回
        ArmsUtils.snackbarText("修改密码成功");
        loginPsw();
    }

    @Override
    public void registerSuc(LoginResponse loginResponse) {

    }

    @Override
    public void wxLoginSuc(WxLoginResponse response) {
      WxLoginResponse.TokenVOBean tokenVOBean =  response.getTokenVO();
        if(tokenVOBean != null){
         String token =  tokenVOBean.getToken();
        if(!TextUtils.isEmpty(token)){
            //获取到token
            SavePreference.save(PingtiaoConst.KEY_TOKEN, token);
            EventBus.getDefault().post(new LoginEventTag());
            String phoneNum = response.getPhone();
            JpushManager.setAlias(phoneNum);
            finish();
        }else{
          wechatBindPhone();
        }
      }else{
            wechatBindPhone();
        }
    }
    private void wechatBindPhone(){
        //为获取token 需要绑定手机号
        Bundle bundle = new Bundle();
        bundle.putString(BindPhoneNumActivity.WXCODE,wechatCode);
        startActBundle(bundle,BindPhoneNumActivity.class);
    }

    private String wechatCode=  "";
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginWechat(WechatLoginTag tag){
        wechatCode = tag.code;
        Log.d("dodo","wx code = " + wechatCode);
        ArmsUtils.snackbarText(wechatCode);
        mPresenter.wxLogin(wechatCode);
    }

}
