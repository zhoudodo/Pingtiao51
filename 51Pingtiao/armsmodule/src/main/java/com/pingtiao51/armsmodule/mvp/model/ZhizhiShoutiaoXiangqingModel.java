package com.pingtiao51.armsmodule.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.pingtiao51.armsmodule.mvp.contract.ZhizhiShoutiaoXiangqingContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CloseElectronicNoteRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ModifyPingtiaoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PingtiaoXiangqingRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoXiangqingResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/26/2019 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ZhizhiShoutiaoXiangqingModel extends BaseModel implements ZhizhiShoutiaoXiangqingContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ZhizhiShoutiaoXiangqingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseJson<PingtiaoXiangqingResponse>> getPingtiaoById(long id) {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi.class)
                .getPingtiaoById(new PingtiaoXiangqingRequest(AppUtils.getAppVersionName(), id, "ANDRIOD"));
    }

    @Override
    public Observable<BaseJson<Object>> modifyPingtiao(long noteid, List<String> addUrls) {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi.class)
                .modifyPingtiao(new ModifyPingtiaoRequest(
                        AppUtils.getAppVersionName(),
                        noteid,
                        "ANDRIOD",
                        addUrls
                ));
    }

    @Override
    public Observable<ResponseBody> downloadFile(String url) {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi.class)
                .downloadFile(url);
    }

    @Override
    public Observable<BaseJson<Object>> closeElectronicNote(long id) {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi.class)
                .closeElectronicNote(new CloseElectronicNoteRequest(
                        AppUtils.getAppVersionName(),
                        id,
                        "ANDRIOD"
                        ,0L
                ));
    }
}