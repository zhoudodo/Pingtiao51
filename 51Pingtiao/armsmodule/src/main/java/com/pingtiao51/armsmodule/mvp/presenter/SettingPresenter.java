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
import com.pingtiao51.armsmodule.mvp.contract.SettingContract;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;


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
public class SettingPresenter extends BasePresenter<SettingContract.Model, SettingContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SettingPresenter(SettingContract.Model model, SettingContract.View rootView) {
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

    public void pushAvatar(String url){
         mModel.pushAvatar(url)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                 .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(mErrorHandler) {
                     @Override
                     public void onNext(BaseJson<Object> objectBaseJson) {
                         if(objectBaseJson.isSuccess()){
                             mRootView.onSuccessPushAvatar();
                         }else{
                             ArmsUtils.snackbarText(objectBaseJson.getMessage());
                         }
                     }
                 });
    }
}
