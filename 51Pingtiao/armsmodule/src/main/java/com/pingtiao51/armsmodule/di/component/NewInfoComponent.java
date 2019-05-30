package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.NewInfoModule;
import com.pingtiao51.armsmodule.mvp.contract.NewInfoContract;

import com.jess.arms.di.scope.FragmentScope;
import com.pingtiao51.armsmodule.mvp.ui.fragment.NewInfoFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/20/2019 17:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = NewInfoModule.class, dependencies = AppComponent.class)
public interface NewInfoComponent {
    void inject(NewInfoFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NewInfoComponent.Builder view(NewInfoContract.View view);

        NewInfoComponent.Builder appComponent(AppComponent appComponent);

        NewInfoComponent build();
    }
}