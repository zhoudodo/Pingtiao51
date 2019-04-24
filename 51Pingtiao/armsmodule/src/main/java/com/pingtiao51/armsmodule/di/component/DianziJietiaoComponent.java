package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.DianziJietiaoModule;
import com.pingtiao51.armsmodule.mvp.contract.DianziJietiaoContract;

import com.jess.arms.di.scope.FragmentScope;
import com.pingtiao51.armsmodule.mvp.ui.fragment.DianziJietiaoFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 21:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DianziJietiaoModule.class, dependencies = AppComponent.class)
public interface DianziJietiaoComponent {
    void inject(DianziJietiaoFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DianziJietiaoComponent.Builder view(DianziJietiaoContract.View view);

        DianziJietiaoComponent.Builder appComponent(AppComponent appComponent);

        DianziJietiaoComponent build();
    }
}