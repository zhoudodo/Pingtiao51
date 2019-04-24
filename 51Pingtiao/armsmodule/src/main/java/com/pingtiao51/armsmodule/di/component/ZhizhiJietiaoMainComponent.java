package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.ZhizhiJietiaoMainModule;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiJietiaoMainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiJietiaoMainActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 18:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ZhizhiJietiaoMainModule.class, dependencies = AppComponent.class)
public interface ZhizhiJietiaoMainComponent {
    void inject(ZhizhiJietiaoMainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ZhizhiJietiaoMainComponent.Builder view(ZhizhiJietiaoMainContract.View view);

        ZhizhiJietiaoMainComponent.Builder appComponent(AppComponent appComponent);

        ZhizhiJietiaoMainComponent build();
    }
}