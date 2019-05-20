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

import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiShoutiaoContract;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailListResponse;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 21:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class ZhizhiShoutiaoPresenter extends BasePresenter<ZhizhiShoutiaoContract.Model, ZhizhiShoutiaoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ZhizhiShoutiaoPresenter(ZhizhiShoutiaoContract.Model model, ZhizhiShoutiaoContract.View rootView) {
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


    public void getPingtiaoList(
            String enoteType,
            int page,
            String queryName,
            String queryScopeType,
            int size,
            String sortType,
            String userRoleType,
            String loanPeriodType,
            String remainderRepayDaysType
    ){
        mModel.getPingtiaoList(enoteType, page, queryName, queryScopeType, size, sortType, userRoleType,loanPeriodType,remainderRepayDaysType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<PingtiaoDetailListResponse>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<PingtiaoDetailListResponse> rep) {
                        if(rep.isSuccess()) {
                            mRootView.onSucPingtiaoList(rep.getData());
                        }else{
                            mRootView.onErrorPingtiaoList(rep.getMessage());
                        }
                    }
                });
    }

}
