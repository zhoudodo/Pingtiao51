package com.pingtiao51.armsmodule.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.mvp.contract.NewInfoContract;
import com.pingtiao51.armsmodule.mvp.model.entity.request.NewsInfoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.NewsInfoResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserDetailInfoResponse;

import java.util.List;


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
public class NewInfoPresenter extends BasePresenter<NewInfoContract.Model, NewInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public NewInfoPresenter(NewInfoContract.Model model, NewInfoContract.View rootView) {
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

    public void getBanner(String category, int page, int size) {
        int showinbanner = 1;
        mModel.getBanner(new NewsInfoRequest(
                category,
                page,
                showinbanner,
                size
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<NewsInfoResponse>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<NewsInfoResponse> listBaseJson) {
                        if (listBaseJson.isSuccess()) {
                            mRootView.onSucBanner(listBaseJson.getData());
                        } else {
                            ArmsUtils.snackbarText(listBaseJson.getMessage());
                        }
                    }
                });
    }

    public void refreshNews(String category, int page, int size) {
        int showinbanner = 0;
        mModel.refreshNews(new NewsInfoRequest(
                category,
                page,
                showinbanner,
                size
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<NewsInfoResponse>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<NewsInfoResponse> listBaseJson) {
                        if (listBaseJson.isSuccess()) {
                            mRootView.onSucRefreshNewsList(listBaseJson.getData());
                        } else {
                            ArmsUtils.snackbarText(listBaseJson.getMessage());
                        }
                    }
                });
    }

    public void loadMoreNews(String category, int page, int size) {
        int showinbanner = 0;
        mModel.loadMoreNews(new NewsInfoRequest(
                category,
                page,
                showinbanner,
                size
        ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<NewsInfoResponse>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<NewsInfoResponse> listBaseJson) {
                        if (listBaseJson.isSuccess()) {
                            mRootView.onSucLoadMore(listBaseJson.getData());
                        } else {
                            ArmsUtils.snackbarText(listBaseJson.getMessage());
                        }
                    }
                });
    }
}
