package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.AddBankCardModule;
import com.pingtiao51.armsmodule.mvp.contract.AddBankCardContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.AddBankCardActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 17:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddBankCardModule.class, dependencies = AppComponent.class)
public interface AddBankCardComponent {
    void inject(AddBankCardActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddBankCardComponent.Builder view(AddBankCardContract.View view);

        AddBankCardComponent.Builder appComponent(AppComponent appComponent);

        AddBankCardComponent build();
    }
}