package com.pingtiao51.armsmodule.mvp.ui.helper;

import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 验证码 请求 流程简化
 */
public class YzmRequest {

    public interface YzmReqListener{
        void getYzmCode();
    }

    private YzmReqListener mYzmReqListener;

    public void setmYzmReqListener(YzmReqListener mYzmReqListener) {
        this.mYzmReqListener = mYzmReqListener;
    }

    private AuthCodeTimer authTimer;
    private TextView mGetYzm;
    private EditText mEditText;
    public YzmRequest(TextView getYzm,EditText et){
        this.mGetYzm = getYzm;
        this.mEditText = et;
    }
    public void getPhoneYzm() {
        if (!EditCheckHelper.checkInputPhoneToast(mEditText)) {
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
            mGetYzm.setText("重发");
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
            mGetYzm.setText(String.format("重发(%s)", millisUntilFinished / 1000));
            mGetYzm.setEnabled(false);
        }
    }



    //获取验证码
    public void getAuthCode() {
        if(mYzmReqListener != null){
            mYzmReqListener.getYzmCode();
        }
    }
}
