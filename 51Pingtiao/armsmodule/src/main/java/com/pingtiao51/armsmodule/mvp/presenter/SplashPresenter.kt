package com.pingtiao51.armsmodule.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.pingtiao51.armsmodule.mvp.contract.SplashContract
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import timber.log.Timber
import java.util.jar.Manifest


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/03/2019 00:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class SplashPresenter
@Inject
constructor(model: SplashContract.Model, rootView: SplashContract.View) :
        BasePresenter<SplashContract.Model, SplashContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    @Inject
    lateinit var mRxPermission: RxPermissions


    fun requestPermision() {
        mRxPermission.requestEach(
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).subscribe(Consumer { permission ->
            disPermissionName(permission)
            if (permission.name == android.Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                if(canNext) {
                    mRootView.getPermissionSuccess()
                }

            }
        }).isDisposed

    }

    private fun disPermissionName(permission: Permission) {
        when (permission.name) {
            android.Manifest.permission.READ_PHONE_STATE -> {
                disPermissionStatus(android.Manifest.permission.READ_PHONE_STATE, permission)
            }
            android.Manifest.permission.CAMERA -> {
                disPermissionStatus(android.Manifest.permission.CAMERA, permission)
            }
            android.Manifest.permission.READ_EXTERNAL_STORAGE -> {
                disPermissionStatus(android.Manifest.permission.READ_EXTERNAL_STORAGE, permission)
            }
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                disPermissionStatus(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, permission)
            }
        }
    }

    var canNext = true;
    private fun disPermissionStatus(name: String, permission: Permission) {
        if (permission.granted) {
            //权限获取
            Timber.d("permission name  = $name  is granted")
        } else if (permission.shouldShowRequestPermissionRationale) {
            Timber.d("permission name  = $name  is denied but 没有选择不再询问")
            when (permission.name) {
                android.Manifest.permission.READ_PHONE_STATE -> {
                    mRootView.getPermissionFail(true)
                    canNext = false;
                }
            }
        } else {
            Timber.d("permission name  = $name  is denied but 选择了不再询问")
            when (permission.name) {
                android.Manifest.permission.READ_PHONE_STATE -> {
                    mRootView.getPermissionFail(true)
                    canNext = false;
                }
            }

        }

    }


    override fun onDestroy() {
        super.onDestroy();
    }


}
