package com.pingtiao51.armsmodule.mvp.model;

import android.annotation.SuppressLint;
import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.pingtiao51.armsmodule.app.utils.encode.MD5Utils;
import com.pingtiao51.armsmodule.mvp.contract.LoginContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.UserService;
import com.pingtiao51.armsmodule.mvp.model.entity.User;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CodeLoginRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ResetPswRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.SendCodeRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.WxLoginRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.LoginResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.WxLoginResponse;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }






    @Override
    public Observable<BaseJson<Object>> sendPhoneCode(String phoneNum,String type) {
        return    mRepositoryManager.obtainRetrofitService(UserService.class)
                .sendCodeRequest(new SendCodeRequest(AppUtils.getAppVersionName(),"ANDRIOD",phoneNum,type));
    }

    @Override
    public Observable<BaseJson<LoginResponse>> vcodeRegister() {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public Observable<BaseJson<LoginResponse>> codeOrPswLogin(boolean isCodeLogin,String psw,long phoneNum) {
        if(isCodeLogin) {
            return mRepositoryManager.obtainRetrofitService(UserService.class)
                    .codeLoginRequest(new CodeLoginRequest(
                            AppUtils.getAppVersionName(),
                            PhoneUtils.getDeviceId(),
                            null, null, "ANDRIOD",
                            null,phoneNum,null,"ANDROID_YINGYONGBAO",
                            psw
                            ));
        }else{
            return mRepositoryManager.obtainRetrofitService(UserService.class)
                    .codeLoginRequest(new CodeLoginRequest(
                            AppUtils.getAppVersionName(),
                            PhoneUtils.getDeviceId(),
                            null, null, "ANDRIOD",
                            MD5Utils.getMD5ofStr(psw),
                            phoneNum,null,"ANDROID_YINGYONGBAO",
                           null
                    ));
        }
    }

    @Override
    public Observable<BaseJson<Object>> resetLoginPassword(long phoneNum, String code, String psw) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .resetLoginPassword(new ResetPswRequest(
                        AppUtils.getAppVersionName(),
                        "ANDRIOD",
                        MD5Utils.getMD5ofStr(psw),
                        phoneNum,
                        code

                ));
    }


    @SuppressLint("MissingPermission")
    @Override
    public Observable<BaseJson<WxLoginResponse>> wxLogin(String code) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .wxLogin(new WxLoginRequest(
                        AppUtils.getAppVersionName(),
                        code,
                        PhoneUtils.getDeviceId(),
                        null, null, "ANDRIOD",
                        null,"ANDROID_YINGYONGBAO"
                ));
    }


}