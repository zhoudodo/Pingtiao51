package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.ZhizhiShoutiaoMainModule;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiShoutiaoMainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoMainActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 14:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ZhizhiShoutiaoMainModule.class, dependencies = AppComponent.class)
public interface ZhizhiShoutiaoMainComponent {
    void inject(ZhizhiShoutiaoMainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ZhizhiShoutiaoMainComponent.Builder view(ZhizhiShoutiaoMainContract.View view);

        ZhizhiShoutiaoMainComponent.Builder appComponent(AppComponent appComponent);

        ZhizhiShoutiaoMainComponent build();
    }
}