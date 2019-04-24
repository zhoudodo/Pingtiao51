package com.pingtiao51.armsmodule.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.mvp.contract.NewPingtiaoContract;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomeBannerResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomeMessageScrollResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomePageComResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingTiaoSeachResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.ShouHuanKuanResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.banner.BannerParentInterface;
import com.pingtiao51.armsmodule.mvp.model.entity.response.rvadapter.HomeParentInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


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
public class NewPingtiaoPresenter extends BasePresenter<NewPingtiaoContract.Model, NewPingtiaoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public NewPingtiaoPresenter(NewPingtiaoContract.Model model, NewPingtiaoContract.View rootView) {
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

    public void getShouHuanKuanInfos(){
        mModel.getShouHuanKuan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<ShouHuanKuanResponse>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<ShouHuanKuanResponse> shouHuanKuanResponseBaseJson) {
                        if(shouHuanKuanResponseBaseJson.isSuccess()){
                            mRootView.sucShouHuanKuan(shouHuanKuanResponseBaseJson.getData());
                        }
                    }
                });
    }


    public void  getHomeBanner(){
        mModel.getHomeBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<HomeBannerResponse>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<List<HomeBannerResponse>> bannerList) {
                        if(bannerList.isSuccess()){
                            List<HomeBannerResponse> tempList = bannerList.getData();
                            List<BannerParentInterface> banners = new ArrayList<>();
                            for(HomeBannerResponse temp: tempList){
                                banners.add(temp);
                            }
                            mRootView.sucBanner(banners);
                        }
                    }
                });
    }

    public void getPingTiaoFiveHistory(){
        mModel.getPingtiaoSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<PingTiaoSeachResponse>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<List<PingTiaoSeachResponse>> listBaseJson) {
                            mRootView.sucRv(listBaseJson.getData());
                    }
                });

    }

    /**
     * 开启定时任务
     */
    public void startTimerTask(){
        Observable.interval(0,40,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Long>(mErrorHandler) {
                    @Override
                    public void onNext(Long aLong) {
//                        Log.d("dodo","定时器运行中 getHomeMessage");
                        getHomeMessage();
                    }
                });
    }

    /**
     * 获取滚动消息
     */
    public void getHomeMessage(){
        mModel.getHomeMessageScrolls()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<HomeMessageScrollResponse>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<List<HomeMessageScrollResponse>> listBaseJson) {
                            if(listBaseJson.isSuccess()){
                                mRootView.sucMessage(listBaseJson.getData());
                            }
                    }
                });
    }

    /**
     * 获得功能入口
     */
    public void getFunctions(){
        mModel.getFunctions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<List<HomePageComResponse>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<List<HomePageComResponse>> listBaseJson) {
                            if(listBaseJson.isSuccess()){
                                mRootView.sucFunction(listBaseJson.getData());
                            }
                    }
                });
    }

}

