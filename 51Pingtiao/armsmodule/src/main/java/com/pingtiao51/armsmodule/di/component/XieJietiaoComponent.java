package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.XieJietiaoModule;
import com.pingtiao51.armsmodule.mvp.contract.XieJietiaoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.XieJietiaoActivity;


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
@Component(modules = XieJietiaoModule.class, dependencies = AppComponent.class)
public interface XieJietiaoComponent {
    void inject(XieJietiaoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        XieJietiaoComponent.Builder view(XieJietiaoContract.View view);

        XieJietiaoComponent.Builder appComponent(AppComponent appComponent);

        XieJietiaoComponent build();
    }
}