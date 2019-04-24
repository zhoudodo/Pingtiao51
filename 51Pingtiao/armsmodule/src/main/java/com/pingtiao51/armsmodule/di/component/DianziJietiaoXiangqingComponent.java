package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.DianziJietiaoXiangqingModule;
import com.pingtiao51.armsmodule.mvp.contract.DianziJietiaoXiangqingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/26/2019 15:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DianziJietiaoXiangqingModule.class, dependencies = AppComponent.class)
public interface DianziJietiaoXiangqingComponent {
    void inject(DianziJietiaoXiangqingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DianziJietiaoXiangqingComponent.Builder view(DianziJietiaoXiangqingContract.View view);

        DianziJietiaoXiangqingComponent.Builder appComponent(AppComponent appComponent);

        DianziJietiaoXiangqingComponent build();
    }
}