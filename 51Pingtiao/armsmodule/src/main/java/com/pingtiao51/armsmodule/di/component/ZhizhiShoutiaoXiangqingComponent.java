package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.ZhizhiShoutiaoXiangqingModule;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiShoutiaoXiangqingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoXiangqingActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/26/2019 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ZhizhiShoutiaoXiangqingModule.class, dependencies = AppComponent.class)
public interface ZhizhiShoutiaoXiangqingComponent {
    void inject(ZhizhiShoutiaoXiangqingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ZhizhiShoutiaoXiangqingComponent.Builder view(ZhizhiShoutiaoXiangqingContract.View view);

        ZhizhiShoutiaoXiangqingComponent.Builder appComponent(AppComponent appComponent);

        ZhizhiShoutiaoXiangqingComponent build();
    }
}