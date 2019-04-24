package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.ZhizhiMobanYulantuModule;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiMobanYulantuContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiMobanYulantuActivity;


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
@ActivityScope
@Component(modules = ZhizhiMobanYulantuModule.class, dependencies = AppComponent.class)
public interface ZhizhiMobanYulantuComponent {
    void inject(ZhizhiMobanYulantuActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ZhizhiMobanYulantuComponent.Builder view(ZhizhiMobanYulantuContract.View view);

        ZhizhiMobanYulantuComponent.Builder appComponent(AppComponent appComponent);

        ZhizhiMobanYulantuComponent build();
    }
}