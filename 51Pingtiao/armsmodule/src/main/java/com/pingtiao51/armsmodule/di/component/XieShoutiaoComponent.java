package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.XieShoutiaoModule;
import com.pingtiao51.armsmodule.mvp.contract.XieShoutiaoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.XieShoutiaoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 12:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = XieShoutiaoModule.class, dependencies = AppComponent.class)
public interface XieShoutiaoComponent {
    void inject(XieShoutiaoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        XieShoutiaoComponent.Builder view(XieShoutiaoContract.View view);

        XieShoutiaoComponent.Builder appComponent(AppComponent appComponent);

        XieShoutiaoComponent build();
    }
}