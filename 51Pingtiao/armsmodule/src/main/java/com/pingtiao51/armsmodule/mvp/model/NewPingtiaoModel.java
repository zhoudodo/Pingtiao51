package com.pingtiao51.armsmodule.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.pingtiao51.armsmodule.mvp.contract.NewPingtiaoContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.HomeApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.HomeBannerRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PingTiaoSeachRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ShouHuanKuanRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomeBannerResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomeMessageScrollResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomePageComResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingTiaoSeachResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.ShouHuanKuanResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/11/2019 16:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class NewPingtiaoModel extends BaseModel implements NewPingtiaoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public NewPingtiaoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<ShouHuanKuanResponse>> getShouHuanKuan() {
        return mRepositoryManager.obtainRetrofitService(HomeApi.class)
                .getShouHuanKuan(new ShouHuanKuanRequest(AppUtils.getAppVersionName(),"ANDRIOD"));
    }

    @Override
    public Observable<BaseJson<List<HomeBannerResponse>>> getHomeBanners() {
        return mRepositoryManager.obtainRetrofitService(HomeApi.class)
                .getHomeBanners(new HomeBannerRequest(AppUtils.getAppVersionName(),"INDEX","ANDRIOD","ANDROID"));
    }

    @Override
    public Observable<BaseJson<List<PingTiaoSeachResponse>>> getPingtiaoSearch() {
        return mRepositoryManager.obtainRetrofitService(HomeApi.class)
                .getPingTiaoSearch(new PingTiaoSeachRequest(AppUtils.getAppVersionName(),"ELECTRONIC","ANDRIOD"));
    }

    @Override
    public Observable<BaseJson<List<HomeMessageScrollResponse>>> getHomeMessageScrolls() {
        return mRepositoryManager.obtainRetrofitService(HomeApi.class)
                .getHomeMessageScroll();
    }

    @Override
    public Observable<BaseJson<List<HomePageComResponse>>> getFunctions() {
        return mRepositoryManager.obtainRetrofitService(HomeApi.class)
                .getFunctions(AppUtils.getAppVersionName(),"APP","ANDROID");
    }
}