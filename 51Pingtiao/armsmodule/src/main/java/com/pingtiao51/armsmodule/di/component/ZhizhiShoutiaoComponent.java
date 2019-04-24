package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.ZhizhiShoutiaoModule;
import com.pingtiao51.armsmodule.mvp.contract.ZhizhiShoutiaoContract;

import com.jess.arms.di.scope.FragmentScope;
import com.pingtiao51.armsmodule.mvp.ui.fragment.ZhizhiShoutiaoFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 21:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ZhizhiShoutiaoModule.class, dependencies = AppComponent.class)
public interface ZhizhiShoutiaoComponent {
    void inject(ZhizhiShoutiaoFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ZhizhiShoutiaoComponent.Builder view(ZhizhiShoutiaoContract.View view);

        ZhizhiShoutiaoComponent.Builder appComponent(AppComponent appComponent);

        ZhizhiShoutiaoComponent build();
    }
}