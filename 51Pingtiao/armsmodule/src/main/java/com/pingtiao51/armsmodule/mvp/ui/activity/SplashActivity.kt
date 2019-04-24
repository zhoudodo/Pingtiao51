package com.pingtiao51.armsmodule.mvp.ui.activity

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.pingtiao51.armsmodule.R
import com.pingtiao51.armsmodule.di.component.DaggerSplashComponent
import com.pingtiao51.armsmodule.di.module.SplashModule
import com.pingtiao51.armsmodule.mvp.contract.SplashContract
import com.pingtiao51.armsmodule.mvp.presenter.SplashPresenter
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_splash.*
import me.jessyan.autosize.utils.AutoSizeUtils
import java.util.concurrent.TimeUnit


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/03/2019 00:04
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
class SplashActivity : BaseArmsActivity<SplashPresenter>(), SplashContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .splashModule(SplashModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_splash //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }



    var welcomeFlag = true
    override fun initData(savedInstanceState: Bundle?) {
        mPresenter?.requestPermision()
        initViewPager()
        come_in.setOnClickListener { it: View? ->
            main_layout.smoothScrollTo(AutoSizeUtils.dp2px(this, 375f), 0)
            Observable.timer(1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer { it: Long? -> go2Main() }, Consumer{ it: Throwable? -> })
                    .isDisposed
        }
          welcomeFlag =  SavePreference.getBooleanDefaultTrue(this,PingtiaoConst.WELCOME_FLAG)
        if(welcomeFlag){
            guide_relative.visibility = View.VISIBLE
            SavePreference.save(PingtiaoConst.WELCOME_FLAG,false);
        }else{
            guide_relative.visibility = View.GONE
            showSplashLayout();
        }
    }

     fun showSplashLayout() {
         if(!welcomeFlag && isPermissionSuc) {
             Observable.timer(1000, TimeUnit.MILLISECONDS)
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(
                             Consumer { it: Long? ->
                                 go2Main()
                             },
                             Consumer { it: Throwable? ->

                             })
                     .isDisposed
         }
    }



    override fun go2Main() {
        startAct(MainActivity::class.java)
        finish()
    }



    override fun activeAppSuccess() {
    }
    var isPermissionSuc = false
    override fun getPermissionSuccess() {
//        go2Main()
        isPermissionSuc = true
        showSplashLayout()
    }

    override fun getPermissionFail(neverAsk: Boolean?) {
        if(neverAsk!!){
            ArmsUtils.snackbarText("手机状态权限获取失败，即将退出App")
            main_layout.postDelayed(Runnable { -> finish()},2000)
        }
    }

    private fun initViewPager() {
        view_pager.adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return 2
            }

            override fun isViewFromObject(view: View, o: Any): Boolean {
                return view === o
            }


            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                var view: View? = null
                when (position) {
                    0 -> view = View.inflate(baseContext, R.layout.guide_layout1, null)
                    1 -> view = View.inflate(baseContext, R.layout.guide_layout2, null)
                }

                container.addView(view)
                return view!!
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(i: Int) {
                initIndicator(i)
                if(i === 1){
                    come_in.visibility = View.VISIBLE
                }else{
                    come_in.visibility = View.GONE
                }
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })
    }

    private fun initIndicator(i: Int) {
        img1.setImageDrawable(resources.getDrawable(R.drawable.guide_circle_bg_ececec))
        img2.setImageDrawable(resources.getDrawable(R.drawable.guide_circle_bg_ececec))
//        img3.setImageDrawable(resources.getDrawable(R.drawable.moban_hui))

        when (i) {
            0 -> img1.setImageDrawable(resources.getDrawable(R.drawable.guide_circle_bg_d0d0d0))
            1 -> img2.setImageDrawable(resources.getDrawable(R.drawable.guide_circle_bg_d0d0d0))
//            2 -> img3.setImageDrawable(resources.getDrawable(R.drawable.moban_lan))
        }
    }

}
