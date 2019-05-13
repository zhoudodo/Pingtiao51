package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.HuankuanFangshiModule;
import com.pingtiao51.armsmodule.mvp.contract.HuankuanFangshiContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.HuankuanFangshiActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/29/2019 18:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HuankuanFangshiModule.class, dependencies = AppComponent.class)
public interface HuankuanFangshiComponent {
    void inject(HuankuanFangshiActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HuankuanFangshiComponent.Builder view(HuankuanFangshiContract.View view);

        HuankuanFangshiComponent.Builder appComponent(AppComponent appComponent);

        HuankuanFangshiComponent build();
    }
}