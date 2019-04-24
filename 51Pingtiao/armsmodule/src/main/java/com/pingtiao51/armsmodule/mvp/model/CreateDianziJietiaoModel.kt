package com.pingtiao51.armsmodule.mvp.model

import android.app.Application
import com.blankj.utilcode.util.AppUtils
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.pingtiao51.armsmodule.mvp.contract.CreateDianziJietiaoContract
import com.pingtiao51.armsmodule.mvp.model.api.service.UserService
import com.pingtiao51.armsmodule.mvp.model.entity.request.UserDetailInfoRequest
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserDetailInfoResponse
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/16/2019 13:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CreateDianziJietiaoModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CreateDianziJietiaoContract.Model {


    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
    override fun getUserDetailInfo(): Observable<BaseJson<UserDetailInfoResponse>?> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java)
                .getUserDetailInfos(UserDetailInfoRequest(AppUtils.getAppVersionName(),"ANDRIOD"))
    }
}
