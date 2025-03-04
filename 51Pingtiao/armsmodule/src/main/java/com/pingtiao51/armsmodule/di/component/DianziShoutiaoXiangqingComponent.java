package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.DianziShoutiaoXiangqingModule;
import com.pingtiao51.armsmodule.mvp.contract.DianziShoutiaoXiangqingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziShoutiaoXiangqingActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/26/2019 15:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DianziShoutiaoXiangqingModule.class, dependencies = AppComponent.class)
public interface DianziShoutiaoXiangqingComponent {
    void inject(DianziShoutiaoXiangqingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DianziShoutiaoXiangqingComponent.Builder view(DianziShoutiaoXiangqingContract.View view);

        DianziShoutiaoXiangqingComponent.Builder appComponent(AppComponent appComponent);

        DianziShoutiaoXiangqingComponent build();
    }
}