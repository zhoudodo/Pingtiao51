package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.YulanJietiaoModule;
import com.pingtiao51.armsmodule.mvp.contract.YulanJietiaoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.YulanJietiaoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/18/2019 16:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = YulanJietiaoModule.class, dependencies = AppComponent.class)
public interface YulanJietiaoComponent {
    void inject(YulanJietiaoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        YulanJietiaoComponent.Builder view(YulanJietiaoContract.View view);

        YulanJietiaoComponent.Builder appComponent(AppComponent appComponent);

        YulanJietiaoComponent build();
    }
}