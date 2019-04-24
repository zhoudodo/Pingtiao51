package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.ZhizhiMobanModule;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiMobanContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiMobanActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 18:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ZhizhiMobanModule.class, dependencies = AppComponent.class)
public interface ZhizhiMobanComponent {
    void inject(ZhizhiMobanActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ZhizhiMobanComponent.Builder view(ZhizhiMobanContract.View view);

        ZhizhiMobanComponent.Builder appComponent(AppComponent appComponent);

        ZhizhiMobanComponent build();
    }
}