package com.pingtiao51.armsmodule.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.pingtiao51.armsmodule.mvp.contract.ZhizhiShoutiaoMainContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.beifenZhizhiJietiaoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.beifenZhizhiShoutiaoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 14:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ZhizhiShoutiaoMainModel extends BaseModel implements ZhizhiShoutiaoMainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ZhizhiShoutiaoMainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseJson<Object>> beifenZhizhiShoutiao(String amount, String borrower, String comment, String lender, String repaymentDate, List<String> urls) {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi.class)
                .beifenZhizhiShoutiao(new beifenZhizhiShoutiaoRequest(
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
}