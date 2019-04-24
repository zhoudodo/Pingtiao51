package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.JietiaoMobanVpModule;
import com.pingtiao51.armsmodule.mvp.contract.JietiaoMobanVpContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.JietiaoMobanVpActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/01/2019 17:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = JietiaoMobanVpModule.class, dependencies = AppComponent.class)
public interface JietiaoMobanVpComponent {
    void inject(JietiaoMobanVpActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        JietiaoMobanVpComponent.Builder view(JietiaoMobanVpContract.View view);

        JietiaoMobanVpComponent.Builder appComponent(AppComponent appComponent);

        JietiaoMobanVpComponent build();
    }
}