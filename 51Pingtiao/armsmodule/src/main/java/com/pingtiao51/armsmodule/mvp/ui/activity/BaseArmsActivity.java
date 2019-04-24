package com.pingtiao51.armsmodule.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ================================================
 * <p>
 * Created by NIRVANA on 2018/5/28
 * Contact with <zwq651406441@gmail.com>
 * ================================================
 */
public abstract class BaseArmsActivity<P extends IPresenter> extends BaseActivity<P> implements IView {

    private KProgressHUD mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //初始化ProgressBar
        mProgressDialog = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中...");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoading() {
        Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (mProgressDialog != null) {
                            mProgressDialog.setLabel("加载中...")
                                    .show();
                        }
                    }
                }).isDisposed();

    }

    public void showLoading(String msg) {
        Observable.just(msg)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (mProgressDialog != null) {
                            mProgressDialog
                                    .setLabel(msg)
                                    .show();
                        }
                    }
                }).isDisposed();
    }


    @Override
    public void hideLoading() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                })
                .isDisposed();
    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }


    @Override
    public void killMyself() {
        finish();
    }

//    public void go2Login() {
//        ArmsUtils.obtainAppComponentFromContext(this).appManager().killActivity(LoginActivity.class);
//        launchActivity(new Intent(this, LoginActivity.class));
//    }

    @Override
    protected void onDestroy() {
        //隐藏键盘
        KeyboardUtils.hideSoftInput(this);
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        super.onDestroy();
    }

    protected void startAct(@NonNull final Class<? extends Activity> clz) {
        ActivityUtils.startActivity(clz);
    }
    protected void startActBundle(Bundle extras,@NonNull final Class<? extends Activity> clz) {
        ActivityUtils.startActivity(extras,clz);
    }


}
