package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.YulanShoutiaoModule;
import com.pingtiao51.armsmodule.mvp.contract.YulanShoutiaoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.YulanShoutiaoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 14:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = YulanShoutiaoModule.class, dependencies = AppComponent.class)
public interface YulanShoutiaoComponent {
    void inject(YulanShoutiaoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        YulanShoutiaoComponent.Builder view(YulanShoutiaoContract.View view);

        YulanShoutiaoComponent.Builder appComponent(AppComponent appComponent);

        YulanShoutiaoComponent build();
    }
}