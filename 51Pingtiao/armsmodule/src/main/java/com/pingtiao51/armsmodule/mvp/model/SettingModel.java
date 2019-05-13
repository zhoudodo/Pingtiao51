package com.pingtiao51.armsmodule.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.pingtiao51.armsmodule.mvp.contract.SettingContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.UserService;
import com.pingtiao51.armsmodule.mvp.model.entity.request.UserHeaderSettingRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/13/2019 17:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SettingModel extends BaseModel implements SettingContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SettingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<Object>> pushAvatar(String url) {
        ArrayList<String> list = new ArrayList();
        list.add(url);
        return mRepositoryManager.obtainRetrofitService(UserService.class).userHeadSetting(new UserHeaderSettingRequest(
                AppUtils.getAppVersionName(),
                "ANDRIOD",
                list
        ));
    }
}