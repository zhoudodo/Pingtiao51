package com.pingtiao51.armsmodule.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.pingtiao51.armsmodule.mvp.contract.ZhizhiJietiaoContract;
import com.pingtiao51.armsmodule.mvp.model.api.service.PingtiaoApi;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PingtiaoDetailListRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailListResponse;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 21:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class ZhizhiJietiaoModel extends BaseModel implements ZhizhiJietiaoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ZhizhiJietiaoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseJson<PingtiaoDetailListResponse>> getPingtiaoList(String enoteType, int page, String queryName, String queryScopeType, int size, String sortType, String userRoleType
            ,
                                                                            String loanPeriodType,
                                                                            String remainderRepayDaysType) {
        return mRepositoryManager.obtainRetrofitService(PingtiaoApi.class)
                .getPingtiaoListDetails(new PingtiaoDetailListRequest(
                        AppUtils.getAppVersionName(),
                        enoteType,
                        "ANDRIOD",
                        page,
                        queryName,
                        queryScopeType,
                        size,
                        sortType,
                        userRoleType,
                        loanPeriodType,
                        remainderRepayDaysType
                ));
    }

}