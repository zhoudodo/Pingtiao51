package com.pingtiao51.armsmodule.mvp.model

import android.app.Application
import com.blankj.utilcode.util.AppUtils
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.pingtiao51.armsmodule.mvp.contract.MainContract
import com.pingtiao51.armsmodule.mvp.model.api.service.HomeApi
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi
import com.pingtiao51.armsmodule.mvp.model.entity.request.CheckUpdateRequest
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson
import com.pingtiao51.armsmodule.mvp.model.entity.response.CheckUpdateResponse
import io.reactivex.Observable
import okhttp3.ResponseBody


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/04/2019 11:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class MainModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MainContract.Model {


    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }

    override fun checkUpdate(): Observable<BaseJson<CheckUpdateResponse>?> {
        return mRepositoryManager.obtainRetrofitService(HomeApi::class.java)
                .checkUpdate(CheckUpdateRequest(AppUtils.getAppVersionName(),"ANDRIOD"))
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi::class.java)
                .downloadFile(url)
    }
}
