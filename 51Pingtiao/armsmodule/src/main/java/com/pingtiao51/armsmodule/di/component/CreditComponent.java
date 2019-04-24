package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.CreditModule;
import com.pingtiao51.armsmodule.mvp.contract.CreditContract;

import com.jess.arms.di.scope.FragmentScope;
import com.pingtiao51.armsmodule.mvp.ui.fragment.CreditFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/11/2019 15:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CreditModule.class, dependencies = AppComponent.class)
public interface CreditComponent {
    void inject(CreditFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CreditComponent.Builder view(CreditContract.View view);

        CreditComponent.Builder appComponent(AppComponent appComponent);

        CreditComponent build();
    }
}