package com.pingtiao51.armsmodule.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import butterknife.OnClick

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.pingtiao51.armsmodule.di.component.DaggerCreateJietiaoComponent
import com.pingtiao51.armsmodule.di.module.CreateJietiaoModule
import com.pingtiao51.armsmodule.mvp.contract.CreateJietiaoContract
import com.pingtiao51.armsmodule.mvp.presenter.CreateJietiaoPresenter

import com.pingtiao51.armsmodule.R
import kotlinx.android.synthetic.main.activity_create_jietiao.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/16/2019 13:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
/**
 * 新建借条
 */
class CreateJietiaoActivity : BaseArmsActivity<CreateJietiaoPresenter>(), CreateJietiaoContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCreateJietiaoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .createJietiaoModule(CreateJietiaoModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_create_jietiao //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }



    override fun initData(savedInstanceState: Bundle?) {
            title = "新建"
        xiejietiao.setOnClickListener(View.OnClickListener { it: View? ->
            startAct(CreateDianziJietiaoActivity::class.java)
        })
        xieshoutiao.setOnClickListener(View.OnClickListener { it:View? ->
            startAct(CreateDianziShoutiaoActivity::class.java)
        })
    }


}
