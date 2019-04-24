package com.pingtiao51.armsmodule.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.pingtiao51.armsmodule.mvp.contract.XieJietiaoContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.AddDianziJietiaoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.AuthSignRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/17/2019 21:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class XieJietiaoModel extends BaseModel implements XieJietiaoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public XieJietiaoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<Object>> addJietiao(String amount, String borrower, String lender, String loanDate, String repaymentDate, String payer, String loanUsage, String userType, String yearRate,String identityNo) {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi.class)
                .addDianziJietiao(new AddDianziJietiaoRequest(
                        amount,AppUtils.getAppVersionName(),borrower,identityNo,lender,loanDate,loanUsage,"ANDRIOD",
                        payer,repaymentDate,null,userType,yearRate
                ));
    }

    @Override
    public Observable<BaseJson<Object>> extSign( Integer noteId, String signName,String returnUrl) {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi.class)
                .extSign(new AuthSignRequest(
                        AppUtils.getAppVersionName(),
                        noteId,
                        "ANDRIOD",
                        signName,
                        returnUrl
                ));
    }
}