package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.ShoutiaoMobanVpActivityModule;
import com.pingtiao51.armsmodule.mvp.contract.ShoutiaoMobanVpActivityContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.ShoutiaoMobanVpActivityActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/02/2019 11:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ShoutiaoMobanVpActivityModule.class, dependencies = AppComponent.class)
public interface ShoutiaoMobanVpActivityComponent {
    void inject(ShoutiaoMobanVpActivityActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ShoutiaoMobanVpActivityComponent.Builder view(ShoutiaoMobanVpActivityContract.View view);

        ShoutiaoMobanVpActivityComponent.Builder appComponent(AppComponent appComponent);

        ShoutiaoMobanVpActivityComponent build();
    }
}