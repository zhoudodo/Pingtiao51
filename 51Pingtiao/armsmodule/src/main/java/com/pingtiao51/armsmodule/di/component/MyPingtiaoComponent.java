package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.MyPingtiaoModule;
import com.pingtiao51.armsmodule.mvp.contract.MyPingtiaoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.MyPingtiaoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/14/2019 17:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyPingtiaoModule.class, dependencies = AppComponent.class)
public interface MyPingtiaoComponent {
    void inject(MyPingtiaoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyPingtiaoComponent.Builder view(MyPingtiaoContract.View view);

        MyPingtiaoComponent.Builder appComponent(AppComponent appComponent);

        MyPingtiaoComponent build();
    }
}