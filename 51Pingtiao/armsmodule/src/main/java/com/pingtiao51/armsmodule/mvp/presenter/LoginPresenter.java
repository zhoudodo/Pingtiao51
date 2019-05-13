package com.pingtiao51.armsmodule.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.mvp.contract.LoginContract;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.LoginResponse;


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
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 短信验证码
     */
    public void sendCode(String phoneNum,String type){
        mModel.sendPhoneCode(phoneNum,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(mErrorHandler) {
                    @Override
                     public void onNext(BaseJson<Object> objectBaseJson) {
                            if(objectBaseJson.isSuccess()){
                                   mRootView.sendCodeSuc(objectBaseJson.getData());
                            }else{
                                ArmsUtils.snackbarText(objectBaseJson.getMessage());
                            }
                    }
                });
    }

    public void loginCode(boolean isCodeLogin,String psw,long phoneNum){
        mModel.codeOrPswLogin(isCodeLogin,psw,phoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<LoginResponse>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<LoginResponse> responseBaseJson) {
                        if(responseBaseJson.isSuccess()){
                            mRootView.loginSuc(responseBaseJson.getData(),phoneNum+"");
                        }else{
                            ArmsUtils.snackbarText(responseBaseJson.getMessage());
                        }
                    }
                });
    }

    public void updatePsw(long phoneNum,String authCode,String psw){
            mModel.resetLoginPassword(phoneNum,authCode,psw)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(mErrorHandler) {
                        @Override
                        public void onNext(BaseJson<Object> responseBaseJson) {
                            if(responseBaseJson.isSuccess()){
                                mRootView.changePswSuc(responseBaseJson.getData());
                            }else{
                                ArmsUtils.snackbarText(responseBaseJson.getMessage());
                            }
                        }
                    });
    }
}
