package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.SecureCopyModule;
import com.pingtiao51.armsmodule.mvp.contract.SecureCopyContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.SecureCopyActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 18:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SecureCopyModule.class, dependencies = AppComponent.class)
public interface SecureCopyComponent {
    void inject(SecureCopyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SecureCopyComponent.Builder view(SecureCopyContract.View view);

        SecureCopyComponent.Builder appComponent(AppComponent appComponent);

        SecureCopyComponent build();
    }
}