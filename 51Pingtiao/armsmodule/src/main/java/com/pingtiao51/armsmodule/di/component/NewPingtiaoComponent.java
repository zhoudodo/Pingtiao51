package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.NewPingtiaoModule;
import com.pingtiao51.armsmodule.mvp.contract.NewPingtiaoContract;

import com.jess.arms.di.scope.FragmentScope;
import com.pingtiao51.armsmodule.mvp.ui.fragment.NewPingtiaoFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/11/2019 16:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = NewPingtiaoModule.class, dependencies = AppComponent.class)
public interface NewPingtiaoComponent {
    void inject(NewPingtiaoFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NewPingtiaoComponent.Builder view(NewPingtiaoContract.View view);

        NewPingtiaoComponent.Builder appComponent(AppComponent appComponent);

        NewPingtiaoComponent build();
    }
}