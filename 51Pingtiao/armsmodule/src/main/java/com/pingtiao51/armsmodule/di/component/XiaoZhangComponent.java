package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.XiaoZhangModule;
import com.pingtiao51.armsmodule.mvp.contract.XiaoZhangContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.XiaoZhangActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/29/2019 15:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = XiaoZhangModule.class, dependencies = AppComponent.class)
public interface XiaoZhangComponent {
    void inject(XiaoZhangActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        XiaoZhangComponent.Builder view(XiaoZhangContract.View view);

        XiaoZhangComponent.Builder appComponent(AppComponent appComponent);

        XiaoZhangComponent build();
    }
}