package com.pingtiao51.armsmodule.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.pingtiao51.armsmodule.mvp.contract.ZhizhiShoutiaoContract;
import com.pingtiao51.armsmodule.mvp.model.ZhizhiShoutiaoModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 21:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ZhizhiShoutiaoModule {

    @Binds
    abstract ZhizhiShoutiaoContract.Model bindZhizhiShoutiaoModel(ZhizhiShoutiaoModel model);
}