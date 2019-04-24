package com.pingtiao51.armsmodule.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.concurrent.TimeUnit;

import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public abstract class BaseArmFragment<P extends IPresenter> extends BaseFragment<P> implements IView {

    private KProgressHUD mProgressDialog;
    protected View viewRoot;
    protected Unbinder mUnbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            mProgressDialog = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("加载中...");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void showLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.setLabel("加载中...")
                    .show();
        }
    }

    public void showLoading(String msg) {
        if (mProgressDialog != null) {
            mProgressDialog
                    .setLabel(msg)
                    .show();
        }
    }


    @Override
    public void hideLoading() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                }).isDisposed();
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
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (getActivity() != null) {
            KeyboardUtils.hideSoftInput(getActivity());
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        super.onDestroyView();
    }

    protected void startAct(@NonNull final Class<? extends Activity> clz) {
        ActivityUtils.startActivity(clz);
    }

    protected void startActBundle(Bundle bundle, @NonNull final Class<? extends Activity> clz) {
        ActivityUtils.startActivity(bundle, clz);
    }

}
