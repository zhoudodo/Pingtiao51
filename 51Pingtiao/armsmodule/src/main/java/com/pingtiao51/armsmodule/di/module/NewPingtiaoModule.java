package com.pingtiao51.armsmodule.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.pingtiao51.armsmodule.mvp.contract.NewPingtiaoContract;
import com.pingtiao51.armsmodule.mvp.model.NewPingtiaoModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/11/2019 16:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class NewPingtiaoModule {

    @Binds
    abstract NewPingtiaoContract.Model bindNewPingtiaoModel(NewPingtiaoModel model);
}