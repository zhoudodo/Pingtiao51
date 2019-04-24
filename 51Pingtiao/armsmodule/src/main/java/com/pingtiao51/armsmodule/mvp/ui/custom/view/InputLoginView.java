package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.presenter.LoginPresenter;
import com.pingtiao51.armsmodule.mvp.ui.helper.EditCheckHelper;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InputLoginView extends FrameLayout {

    @BindViews({R.id.rl_login_et_userid, R.id.rl_login_et_password
            , R.id.login_et_auth, R.id.rl_login_et_password_new})
    RelativeLayout[] rlRootView;
    @BindViews({R.id.login_et_userid, R.id.login_et_password
            , R.id.yzm, R.id.login_et_password_new})
    public EditText[] editViews;

    @BindView(R.id.login_cancel)
    ImageView login_cancel;


    @BindView(R.id.get_yzm)
    TextView mGetYzm;
    @BindViews({R.id.login_yanjing, R.id.login_yanjing_news})
    CheckBox[] checkBoxes;
    LoginPresenter mLoginPresenter;

    public void setmLoginPresenter(LoginPresenter L) {
        this.mLoginPresenter = L;
    }

    private int viewType = 0; //子视图type类型

    public int getViewType() {
        return viewType;
    }

    private AuthCodeTimer authTimer;

    @OnClick({R.id.get_yzm, R.id.login_cancel})
    void clickView(View v) {
        switch (v.getId()) {
            case R.id.get_yzm:
                getPhoneYzm();
                break;
            case R.id.login_cancel:
                login_cancel.setVisibility(View.GONE);
                editViews[0].setText("");
                break;
        }
    }


    public InputLoginView(Context context) {
        this(context, null);
    }

    public InputLoginView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputLoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRootView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputLoginView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initRootView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mUnbinder.unbind();
    }

    Unbinder mUnbinder;

    private void initRootView() {
        addView(LayoutInflater.from(getContext()).inflate(R.layout.include_login_layout, this, false));
        mUnbinder = ButterKnife.bind(this);
        initChildView();
    }

    private void initChildView() {
        editViews[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    login_cancel.setVisibility(View.VISIBLE);
                } else {
                    login_cancel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checkBoxes[0].setChecked(false);
        checkBoxes[1].setChecked(false);

        checkBoxes[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editViews[1].setInputType(0x90);
                } else {
                    editViews[1].setInputType(0x81);
                }
            }
        });
        //editText 内容是否明文
        checkBoxes[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editViews[3].setInputType(0x90);
                } else {
                    editViews[1].setInputType(0x81);
                }
            }
        });

    }

    public final static int PSD_LOGIN = 0;//密码登录
    public final static int CODE_LOGIN = 1;//短信登录
    public final static int REGISTER = 2;//账号注册
    public final static int CHANGE_PSD = 3;//忘记密码

    public void setTypeView(int type) {
        mGetYzm.setText("获取验证码");
        mGetYzm.setEnabled(true);
        if(authTimer != null) {
            authTimer.cancel();
            authTimer.isGetAuth = false;
        }
        for(int i=0; i< editViews.length;i++ ){
            if(i>0){
                editViews[i].setText("");
            }
        }
        viewType = type;
        switch (viewType) {
            case 0:  //登录输入密码
                rlRootView[1].setVisibility(VISIBLE);
                rlRootView[2].setVisibility(GONE);
                rlRootView[3].setVisibility(GONE);
                break;
            case 1: //登录输入验证码
                rlRootView[1].setVisibility(GONE);
                rlRootView[2].setVisibility(VISIBLE);
                rlRootView[3].setVisibility(GONE);
                break;
            case 2://账号注册
                rlRootView[1].setVisibility(GONE);
                rlRootView[2].setVisibility(VISIBLE);
                rlRootView[3].setVisibility(VISIBLE);
                break;
            case 3://忘记密码
                rlRootView[1].setVisibility(GONE);
                rlRootView[2].setVisibility(VISIBLE);
                rlRootView[3].setVisibility(VISIBLE);
                break;

        }

    }

    public boolean isCheckInputChontent() {
        if (!EditCheckHelper.checkInputPhoneToast(editViews[0])) {
            return false;
        }
        switch (viewType) {
            case 0:
                return EditCheckHelper.checkInputPasswordToast(editViews[1]);
            case 1:
                return EditCheckHelper.checkInputAuthCodeToast(editViews[2]);
            case 2:
            case 3:
                return EditCheckHelper.checkInputAuthCodeToast(editViews[2]) && EditCheckHelper.checkInputPasswordToast(editViews[3]);
        }
        return true;
    }

    public void getPhoneYzm() {
        if (!EditCheckHelper.checkInputPhoneToast(editViews[0])) {
            return;
        }
        if (authTimer == null) {
            authTimer = new AuthCodeTimer(60000, 1000); // 第一参数是总的时间，第二个是间隔时间
        }
        mGetYzm.setEnabled(false);
        authTimer.start();
    }


    /* 定义一个倒计时的内部类 */
    private class AuthCodeTimer extends CountDownTimer {
        private boolean isGetAuth = false; //是否已经发送请求

        public AuthCodeTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onFinish() {
            if (mGetYzm == null) {
                return;
            }
//            mGetYzm.setText("重发");
            mGetYzm.setText("获取验证码");
            mGetYzm.setEnabled(true);
            isGetAuth = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (mGetYzm == null) {
                return;
            }
            if (!isGetAuth) {
                isGetAuth = true;
                getAuthCode();
            }
//            mGetYzm.setText(String.format("重发(%s)", millisUntilFinished / 1000));
            mGetYzm.setText(String.format("%ss", millisUntilFinished / 1000));
            mGetYzm.setEnabled(false);
        }
    }


    //获取验证码
    public void getAuthCode() {

        String phoneNumber = editViews[0].getText().toString().trim();
        String type = "";
        switch (viewType) {
            case PSD_LOGIN:

                break;
            case CODE_LOGIN:
                type = CodeType.LOGIN.getType();
                break;
            case REGISTER:
                type = CodeType.REGISTER.getType();
                break;
            case CHANGE_PSD:
                type = CodeType.RESET_PASSWORD.getType();
                break;
        }
        mLoginPresenter.sendCode(phoneNumber,type);

    }


    @SuppressLint("CheckResult")
    public void loginPhone() {
        String phoneNum = editViews[0].getText().toString().trim();
        String psw = editViews[1].getText().toString().trim();
        mLoginPresenter.loginCode(false,psw,Long.valueOf(phoneNum));
    }


    //短信登录
    private void loginAuthCode() {
        String mAuthcode = editViews[2].getText().toString().trim();
        String phoneNum = editViews[0].getText().toString().trim();
        mLoginPresenter.loginCode(true, mAuthcode, Long.valueOf(phoneNum));
    }

    //自动登录方法抽取
    public static void autoLogin(String UnameAes, String pwdAes) {

      /*  String actions = "/sd/login.sduds";
        Observable<BaseResp<String>> obsLogin;
        obsLogin = NetHelper.getComObserableBaseResp(actions, MapParamsHelper.getLoginParams(actions, UnameAes, pwdAes));

        getCommonLoginStatus(obsLogin);*/
    }


    @SuppressLint("CheckResult")
    public static void getUserInfo() {
        /*//获取风险测评信息
        UserManagerHelper.getInstance().getRiskInfo();
        //获取用户预约数据
        UserManagerHelper.getInstance().getAppointmentData();

        String actions = "/sd/get_user_profile.sduds";
        Observable<BaseResp<String>> observable = NetHelper.getComObserableBaseResp(actions, MapParamsHelper.getParamsMap(actions));
        NetHelper.doObservable(observable, new JsonCallBack<UserDetailBean>() {

            @Override
            public void onSuccessData(UserDetailBean data) {
                UserManagerHelper.getInstance().updateUserDetailInfo(data);
                DataHelper.doStatisticsClickEvent(EnumHelper.ClickType.LoginBtn);
                EventBus.getDefault().post(new LoginEvent());
            }

            @Override
            public void onFailure(String error) {
                super.onFailure(error);
                EventBus.getDefault().post(new LoginErrorEvent());
            }

        });*/
    }


    //注册手机号
    @SuppressLint("CheckResult")
    public void registerPhone() {
       /* ContextHelper.getRequiredActivity(getContext()).showProDialog("数据请求中...");

        String UnameAes = null;
        String pwdAes = null;
        try {
            UnameAes = AESClientUtil.Encrypt(editViews[0].getText().toString().trim());
            pwdAes = AESClientUtil.Encrypt(editViews[3].getText().toString().trim());
            UserManagerHelper.saveUserPhone(UnameAes);
            SavePreference.save(MTConst.SaveUserInfo.PHONE_PSD, pwdAes);


        } catch (Exception e) {
            e.printStackTrace();
        }

        final String mLoginID = UnameAes;
        final String mPassWord = pwdAes;
        final String mAuthcode = editViews[2].getText().toString().trim();

        //先验证验证码
        String actions = "/sd/check_code.sduds";
        Observable<BaseResp<String>> obsAuthCode;
        obsAuthCode = NetHelper.getComObserableBaseResp(actions, MapParamsHelper.getCheckCodeMap(actions, UnameAes, mAuthcode));

        NetHelper.doObservable(obsAuthCode, new RetrofitCallBack() {
            @Override
            public void onSuccess(String baseResp) {
                registerAccount(mLoginID, mPassWord, mAuthcode);
            }

            @Override
            public void onFailure(String error) {
                ContextHelper.getRequiredActivity(getContext()).dismissProgressDialog();
                ToastUtil.showToast(error);
            }
        });
*/
    }

    //注册账号
    private void registerAccount(String mLoginID, String mPassWord, String mAuthcode) {
  /*      String actions = "/sd/register.sduds";
        Observable<BaseResp<String>> obsRegister = NetHelper.getComObserableBaseResp(actions, MapParamsHelper.getRegisterMap(actions, mLoginID, mPassWord, mAuthcode));
        NetHelper.doObservable(obsRegister, new RetrofitCallBack() {
            @Override
            public void onSuccess(String baseResp) {
                loginPhone();
            }

            @Override
            public void onFailure(String error) {
                ContextHelper.getRequiredActivity(getContext()).dismissProgressDialog();
                ToastUtil.showToast(error);
            }
        });*/
    }


    //找回密码
    private void updatePswlogin() {

        String phoneNum = editViews[0].getText().toString().trim();
        String authCode = editViews[2].getText().toString().trim();
        String psw = editViews[3].getText().toString().trim();

        mLoginPresenter.updatePsw(Long.valueOf(phoneNum),authCode,psw);

    }
    public static enum  CodeType {
        REGISTER("REGISTER"),LOGIN("LOGIN"),RESET_PASSWORD("RESET_PASSWORD"),IDENTITY_AUTH("IDENTITY_AUTH");
        private String type;
        CodeType(String s) {
            this.type = s;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public void loginWithType() {
        if (!isCheckInputChontent()) {
            return;
        }
        switch (viewType) {
            case 0:
                loginPhone();
                break;
            case 1:
                loginAuthCode();
                break;
            case 2:
                registerPhone();
                break;
            case 3:
                updatePswlogin();
                break;
        }

    }

}
