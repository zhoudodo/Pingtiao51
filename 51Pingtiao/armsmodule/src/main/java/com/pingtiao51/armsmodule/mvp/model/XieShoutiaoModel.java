package com.pingtiao51.armsmodule.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.pingtiao51.armsmodule.mvp.contract.XieShoutiaoContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.AddDianziShoutiaoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.AuthSignRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 12:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class XieShoutiaoModel extends BaseModel implements XieShoutiaoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public XieShoutiaoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<String>> addDianziShoutiaotiao(String amount, String borrower, String comment, String lender, String repaymentDate, List<String> urls) {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi.class)
                .addDianziShoutiaotiao(new AddDianziShoutiaoRequest(
                        amount,
                        AppUtils.getAppVersionName(),
                        borrower,
                        comment,
                        lender,
                        "ANDRIOD",
                        repaymentDate,
                        urls
                ));
    }

    @Override
    public Observable<BaseJson<Object>> extSign(String noteid, String name,String returnUrl) {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi.class)
                .extSign(new AuthSignRequest(
                        AppUtils.getAppVersionName(),
                        Integer.valueOf(noteid),
                        "ANDRIOD",
                        name,
                        returnUrl
                ));
    }
}