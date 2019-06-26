package com.pingtiao51.armsmodule.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.pingtiao51.armsmodule.di.module.NewsInfoDetailModule;
import com.pingtiao51.armsmodule.mvp.contract.NewsInfoDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.mvp.ui.activity.NewsInfoDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/24/2019 14:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = NewsInfoDetailModule.class, dependencies = AppComponent.class)
public interface NewsInfoDetailComponent {
    void inject(NewsInfoDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NewsInfoDetailComponent.Builder view(NewsInfoDetailContract.View view);

        NewsInfoDetailComponent.Builder appComponent(AppComponent appComponent);

        NewsInfoDetailComponent build();
    }
}