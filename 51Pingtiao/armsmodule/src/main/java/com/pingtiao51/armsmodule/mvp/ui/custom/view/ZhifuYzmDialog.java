package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pingtiao51.armsmodule.R;
import com.zls.baselib.custom.view.dialog.FrameDialog;

public class ZhifuYzmDialog extends FrameDialog {

    public interface ZhifuComComplete {
        void onSuccess(String s);
    }

    private ZhifuComComplete mZhifuComComplete;

    public ZhifuYzmDialog(Activity context, ZhifuComComplete l) {
        super(context);
        mZhifuComComplete = l;
    }

    public ZhifuYzmDialog(Activity context, int themeStyle) {
        super(context, themeStyle);
    }

    @Override
    protected int getViewIds() {
        return R.layout.dialog_zhifu_yzm_layout;
    }

    TextView mGetYzm;
    ImageView mCloseBtn;
    VerificationCodeInput mVerificationCodeInput;

    @Override
    protected void initView() {
        super.initView();
        mGetYzm = findViews(R.id.get_yzm);
        mCloseBtn = findViewsId(R.id.close_btn, true);
        mVerificationCodeInput = findViews(R.id.input_yzm);
        setCanceledOnTouchOutside(false);
        mVerificationCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String s) {
                //TODO 输入验证码之后的操作
                if(mZhifuComComplete != null){
                    mZhifuComComplete.onSuccess(s);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_btn:
                dismiss();
                break;
        }
    }

    AuthCodeTimer authTimer;

    //开始获取验证码
    public void getPhoneYzm() {
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
        //TODO 获取验证码
    }


}
