package com.pingtiao51.armsmodule.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.pingtiao51.armsmodule.di.module.CreateDianziJietiaoModule

import com.jess.arms.di.scope.ActivityScope
import com.pingtiao51.armsmodule.mvp.ui.activity.CreateDianziJietiaoActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/16/2019 13:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(CreateDianziJietiaoModule::class), dependencies = arrayOf(AppComponent::class))
interface CreateDianziJietiaoComponent {
    fun inject(activity: CreateDianziJietiaoActivity)
}
