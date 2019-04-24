package com.pingtiao51.armsmodule.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.pingtiao51.armsmodule.mvp.contract.CreateDianziShoutiaoContract
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserDetailInfoResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/16/2019 13:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CreateDianziShoutiaoPresenter
@Inject
constructor(model: CreateDianziShoutiaoContract.Model, rootView: CreateDianziShoutiaoContract.View) :
        BasePresenter<CreateDianziShoutiaoContract.Model, CreateDianziShoutiaoContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    override fun onDestroy() {
        super.onDestroy();
    }


    /**
     * 获取用户是否实名认证
     */
    fun getUserDetailInfo(){
        mModel.getUserDetailInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseJson<UserDetailInfoResponse>>(mErrorHandler)
                {
                    override fun onNext(t: BaseJson<UserDetailInfoResponse>) {
                        if(t.isSuccess){
                            mRootView.showUserVertifyDialog(t.data)
                        }
                    }
                })

    }
}
