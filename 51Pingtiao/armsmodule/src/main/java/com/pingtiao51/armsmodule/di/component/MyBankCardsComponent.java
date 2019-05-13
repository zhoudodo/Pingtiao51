package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.MyBankCardsModule;
import com.pingtiao51.armsmodule.mvp.contract.MyBankCardsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.MyBankCardsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/30/2019 13:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyBankCardsModule.class, dependencies = AppComponent.class)
public interface MyBankCardsComponent {
    void inject(MyBankCardsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyBankCardsComponent.Builder view(MyBankCardsContract.View view);

        MyBankCardsComponent.Builder appComponent(AppComponent appComponent);

        MyBankCardsComponent build();
    }
}