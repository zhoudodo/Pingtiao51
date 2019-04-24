package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.DianziShoutiaoModule;
import com.pingtiao51.armsmodule.mvp.contract.DianziShoutiaoContract;

import com.jess.arms.di.scope.FragmentScope;
import com.pingtiao51.armsmodule.mvp.ui.fragment.DianziShoutiaoFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 21:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DianziShoutiaoModule.class, dependencies = AppComponent.class)
public interface DianziShoutiaoComponent {
    void inject(DianziShoutiaoFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DianziShoutiaoComponent.Builder view(DianziShoutiaoContract.View view);

        DianziShoutiaoComponent.Builder appComponent(AppComponent appComponent);

        DianziShoutiaoComponent build();
    }
}