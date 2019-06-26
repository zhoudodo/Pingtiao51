package com.pingtiao51.armsmodule.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.pingtiao51.armsmodule.mvp.contract.NewInfoContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.HomeApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.NewsInfoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.NewsInfoResponse;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/20/2019 17:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class NewInfoModel extends BaseModel implements NewInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public NewInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<NewsInfoResponse>> getBanner(NewsInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(HomeApi.class).getNewsInfo(request);
    }

    @Override
    public Observable<BaseJson<NewsInfoResponse>> refreshNews(NewsInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(HomeApi.class).getNewsInfo(request);
    }

    @Override
    public Observable<BaseJson<NewsInfoResponse>> loadMoreNews(NewsInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(HomeApi.class).getNewsInfo(request);
    }
}