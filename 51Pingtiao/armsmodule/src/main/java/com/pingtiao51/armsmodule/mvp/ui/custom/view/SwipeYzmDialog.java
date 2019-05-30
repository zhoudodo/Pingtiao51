package com.pingtiao51.armsmodule.mvp.ui.custom.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.R;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.swipecaptcha.RxSwipeCaptcha;
import com.zls.baselib.custom.view.dialog.FrameDialog;

import java.util.Random;

public class SwipeYzmDialog extends FrameDialog {

    public interface SwipeYzmInterface {
        public void onSuccess();
    }

    SwipeYzmInterface mSwipeYzmInterface;

    public void setListener(SwipeYzmInterface listener) {
        mSwipeYzmInterface = listener;
    }

    public SwipeYzmDialog(Activity context) {
        super(context);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected int getViewIds() {
        return R.layout.dialog_swipe_yzm_layout;
    }


    ImageView mCancel, mReset;
    RxSwipeCaptcha mRxSwipeCaptcha;
    SeekBar mSeekBar;
    TextView mFailHint;

    @Override
    protected void initView() {
        mCancel = findViewsId(R.id.cancel, true);
        mReset = findViewsId(R.id.reset, true);
        mRxSwipeCaptcha = findViews(R.id.swipeCaptchaView);
        mFailHint = findViews(R.id.fail_hint);
        mSeekBar = findViews(R.id.dragBar);

        mRxSwipeCaptcha.setOnCaptchaMatchCallback(new RxSwipeCaptcha.OnCaptchaMatchCallback() {
            @Override
            public void matchSuccess(RxSwipeCaptcha rxSwipeCaptcha) {
                //swipeCaptcha.createCaptcha();
                if (mSwipeYzmInterface != null) {
                    mSwipeYzmInterface.onSuccess();
                }
                mSeekBar.setEnabled(false);
//                ArmsUtils.snackbarText("验证通过！");
                SwipeYzmDialog.this.dismiss();
            }

            @Override
            public void matchFailed(RxSwipeCaptcha rxSwipeCaptcha) {
                rxSwipeCaptcha.resetCaptcha();
                mSeekBar.setProgress(0);
                mFailHint.setVisibility(View.VISIBLE);
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRxSwipeCaptcha.setCurrentSwipeValue(progress);
                mFailHint.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //随便放这里是因为控件
                mSeekBar.setMax(mRxSwipeCaptcha.getMaxSwipeValue());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mRxSwipeCaptcha.matchCaptcha();
            }
        });
        changeYzPicture();
    }

    private void changeYzPicture(){
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mRxSwipeCaptcha.setImageDrawable(resource);
                mRxSwipeCaptcha.createCaptcha();
            }
        };

        Random random = new Random();
        int randomInt = random.nextInt(10);
        int drawableRes = R.drawable.yzt1;
        switch (randomInt){
            case 0:
                drawableRes = R.drawable.yzt1;
                break;
            case 1:
                drawableRes = R.drawable.yzt2;
                break;
            case 2:
                drawableRes = R.drawable.yzt3;
                break;
            case 3:
                drawableRes = R.drawable.yzt4;
                break;
            case 4:
                drawableRes = R.drawable.yzt5;
                break;
            case 5:
                drawableRes = R.drawable.yzt6;
                break;
            case 6:
                drawableRes = R.drawable.yzt7;
                break;
            case 7:
                drawableRes = R.drawable.yzt8;
                break;
            case 8:
                drawableRes = R.drawable.yzt9;
                break;
            case 9:
                drawableRes = R.drawable.yzt10;
                break;
        }

        //测试从网络加载图片是否ok
        Glide.with(getContext())
                .load(drawableRes)
                .into(simpleTarget);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.reset:
                changeYzPicture();
                mRxSwipeCaptcha.createCaptcha();
                mSeekBar.setEnabled(true);
                mSeekBar.setProgress(0);
                break;
        }
    }

    @Override
    protected void initLocation() {
        WindowManager.LayoutParams params = getWindow()
                .getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.CENTER);
    }

}
