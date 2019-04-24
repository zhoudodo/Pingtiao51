package com.pingtiao51.armsmodule.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.pingtiao51.armsmodule.mvp.contract.XieJietiaoContract;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingTiaoSeachResponse;
import com.pingtiao51.armsmodule.mvp.ui.activity.BaseArmsActivity;

import java.util.List;


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
public class XieJietiaoPresenter extends BasePresenter<XieJietiaoContract.Model, XieJietiaoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public XieJietiaoPresenter(XieJietiaoContract.Model model, XieJietiaoContract.View rootView) {
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

    /**
     * 生成借条
     */
    public void addJietiao(String amount,
                           String borrower,
                           String lender,
                           String loanDate,
                           String repaymentDate,
                           String payer,
                           String loanUsage,
                           String userType,
                           String yearRate,
                           String identityNo) {
        mModel.addJietiao(amount, borrower, lender, loanDate, repaymentDate, payer, loanUsage, userType, yearRate, identityNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ((BaseArmsActivity) mRootView).showLoading("正在生成借条，请等待...");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<Object> listBaseJson) {
                        if (listBaseJson.isSuccess()) {
                            mRootView.sucAddJietiao((String) listBaseJson.getData());
                        }else{
                            ArmsUtils.snackbarText(listBaseJson.getMessage());
                        }
                    }
                });
    }


    public void authSign(
            String noteId,
            String signName,
            String returnUrl
    ) {
        mModel.extSign(Integer.valueOf(noteId), signName,returnUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
//                        ((BaseArmsActivity) mRootView).showLoading("正在准备签章信息...");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson<Object>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<Object> listBaseJson) {
                        if (listBaseJson.isSuccess()) {
                            mRootView.sucAuthSign((String) listBaseJson.getData());
                        }else{
                            ArmsUtils.snackbarText(listBaseJson.getMessage());
                        }
                    }
                });

    }
}
