package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.YulanZhizhiJietiaoModule;
import com.pingtiao51.armsmodule.mvp.contract.YulanZhizhiJietiaoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.YulanZhizhiJietiaoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 13:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = YulanZhizhiJietiaoModule.class, dependencies = AppComponent.class)
public interface YulanZhizhiJietiaoComponent {
    void inject(YulanZhizhiJietiaoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        YulanZhizhiJietiaoComponent.Builder view(YulanZhizhiJietiaoContract.View view);

        YulanZhizhiJietiaoComponent.Builder appComponent(AppComponent appComponent);

        YulanZhizhiJietiaoComponent build();
    }
}