package com.pingtiao51.armsmodule.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.pingtiao51.armsmodule.mvp.contract.ShimingrenzhengContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.UserService;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CarrierAuthRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.SendCodeRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 14:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ShimingrenzhengModel extends BaseModel implements ShimingrenzhengContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ShimingrenzhengModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseJson<Object>> getCarrierAuth(String identityNo,String name,String phone,String vcode) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .getCarrierAuth(new CarrierAuthRequest(
                        AppUtils.getAppVersionName(),
                        null,
                        identityNo,
                        name,
                        "ANDRIOD",
                        phone
//                        vcode
                        ));
    }

    @Override
    public Observable<BaseJson<Object>> sendCodeRequest(String phoneNum,String type) {
        return  mRepositoryManager.obtainRetrofitService(UserService.class)
                .sendCodeRequest(new SendCodeRequest(AppUtils.getAppVersionName(),"ANDRIOD",phoneNum,type));
    }
}