package com.pingtiao51.armsmodule.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.pingtiao51.armsmodule.mvp.contract.ZhizhiMobanYulantuContract;
import com.pingtiao51.armsmodule.mvp.model.ZhizhiMobanYulantuModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/21/2019 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ZhizhiMobanYulantuModule {

    @Binds
    abstract ZhizhiMobanYulantuContract.Model bindZhizhiMobanYulantuModel(ZhizhiMobanYulantuModel model);
}