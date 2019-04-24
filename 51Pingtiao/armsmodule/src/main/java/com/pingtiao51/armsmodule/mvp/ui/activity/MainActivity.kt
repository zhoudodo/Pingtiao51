package com.pingtiao51.armsmodule.mvp.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils

import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.pingtiao51.armsmodule.di.component.DaggerMainComponent
import com.pingtiao51.armsmodule.di.module.MainModule
import com.pingtiao51.armsmodule.mvp.contract.MainContract
import com.pingtiao51.armsmodule.mvp.presenter.MainPresenter

import com.pingtiao51.armsmodule.R
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.ExitAppTag
import com.pingtiao51.armsmodule.mvp.model.entity.eventbus.UnLoginBackTag
import com.pingtiao51.armsmodule.mvp.model.entity.response.CheckUpdateResponse
import com.pingtiao51.armsmodule.mvp.ui.custom.view.AppUpdateDialog
import com.pingtiao51.armsmodule.mvp.ui.fragment.CreditFragment
import com.pingtiao51.armsmodule.mvp.ui.fragment.HomeFragment
import com.pingtiao51.armsmodule.mvp.ui.fragment.MyFragment
import com.pingtiao51.armsmodule.mvp.ui.helper.AppUpdateHelper
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst
import com.pingtiao51.armsmodule.mvp.ui.helper.UrlDecoderHelper
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference
import com.zls.baselib.custom.view.dialog.DialogChooseNormal
import com.zls.baselib.custom.view.dialog.DialogHintNormal
import com.zls.baselib.custom.view.helputils.SelectorUtil
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/04/2019 11:07
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
class MainActivity : BaseArmsActivity<MainPresenter>(), MainContract.View, View.OnClickListener {



    lateinit var imgs: Array<ImageView>
    lateinit var tvs: Array<TextView>
    lateinit var pages: Array<LinearLayout>

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        fragments = arrayOf(
                HomeFragment.newInstance(),
                CreditFragment.newInstance(),
                MyFragment.newInstance()
        )


        pages = arrayOf(
                findViewById(R.id.ll_main_page1),
                findViewById(R.id.ll_main_page2),
                findViewById(R.id.ll_main_page3)
        )

        imgs = arrayOf(
                findViewById(R.id.act_main_iv_page1),
                findViewById(R.id.act_main_iv_page2),
                findViewById(R.id.act_main_iv_page3)
        )

        tvs = arrayOf(
                findViewById(R.id.act_main_tv_page1),
                findViewById(R.id.act_main_tv_page2),
                findViewById(R.id.act_main_tv_page3)
        )


        imgs[0].setImageDrawable(SelectorUtil.getDrawableWithDrawa(R.drawable.shouye_icon, R.drawable.shouye))
        imgs[1].setImageDrawable(SelectorUtil.getDrawableWithDrawa(R.drawable.xinyogn_icon, R.drawable.xinyong))
        imgs[2].setImageDrawable(SelectorUtil.getDrawableWithDrawa(R.drawable.wode_icon, R.drawable.wode))



        for (tvItem in tvs) {
            tvItem.setTextColor(SelectorUtil.getColorStateListSelected(
                    resources.getColor(R.color.black_color_494949),
                    resources.getColor(R.color.tv_main_color_FF814D)))

        }
        for (tvPage in pages) {
            tvPage.setOnClickListener(this)
        }

        if (!fragments!![0].isAdded)
            supportFragmentManager.beginTransaction()
                    .add(R.id.framelayout, fragments!![0])
                    .show(fragments!![0]).commit()


        imgs[0].isSelected = true
        tvs[0].isSelected = true

        mPresenter?.checkUpdate()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun exitApp(tag: ExitAppTag) {
        fragmentChange(0)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun unLoginBack(tag: UnLoginBackTag) {
        fragmentChange(0)
    }


    // 当前fragment的 index
    private var currentTabIndex = 0
    private var fragments: Array<Fragment>? = null

    fun fragmentChange(tag: Int) {
        if (currentTabIndex != tag) {
            val trx = supportFragmentManager
                    .beginTransaction()
            fragments?.get(currentTabIndex)?.let { trx.hide(it) }
            if (!fragments?.get(tag)?.isAdded!!) {
                fragments?.get(tag)?.let { trx.add(R.id.framelayout, it) }
            }
            fragments?.get(tag)?.let { trx.show(it).commitAllowingStateLoss() }
        }
        imgs[currentTabIndex].isSelected = false
        tvs[currentTabIndex].isSelected = false
        // 把当前tab设为选中状态
        imgs[tag].isSelected = true
        tvs[tag].isSelected = true
        currentTabIndex = tag

        //改变标题栏颜色
        //        updateStatusBar(tag == 1 ? Color.TRANSPARENT : Color.BLACK);
    }


    override fun onClick(v: View?) {
        var vid = v?.id
        when (vid) {
            R.id.ll_main_page1 -> {
                fragmentChange(0)
            }
            R.id.ll_main_page2 -> {
                fragmentChange(1)
            }
            R.id.ll_main_page3 -> {
                fragmentChange(2)
            }

        }
    }

    var oneTime = 0L
    var twoTime = 0L
    override fun onBackPressed() {
        twoTime = System.currentTimeMillis()
        if (twoTime - oneTime > 2000) {
            ArmsUtils.snackbarText("再按一次退出程序")
            oneTime = twoTime
        } else {
            System.exit(0)
        }

    }

    var mDialogChooseNormal: DialogChooseNormal? = null
    /**
     * 非强制更新
     */
    fun updateCanCancel() {
        var lastSaveTime = SavePreference.getLong(this, PingtiaoConst.UPDATE_HINT_AGAIN)
        var newTime = System.currentTimeMillis()
        if (newTime < lastSaveTime) {
            return
        }
        if (mDialogChooseNormal == null) {
            mDialogChooseNormal = DialogChooseNormal.ChooseBuilder()
                    .setTitle("发现新版本")
                    .setContext(ActivityUtils.getTopActivity())
                    .setContent("当前版本过低，为了保障您的正常使用，请及时更新")
                    .setBtn1Content("暂不提醒").setOnClickListener1 { dialog, which ->
                        this.mDialogChooseNormal?.dismiss()
                        var calendar = Calendar.getInstance()
                        calendar.time = Date()
                        calendar.add(Calendar.DAY_OF_MONTH,30)
                        var times = calendar.timeInMillis
                        SavePreference.save(PingtiaoConst.UPDATE_HINT_AGAIN, times)
                    }
                    .setBtn1Colort(R.color.gray_color_7D7D7D)
                    .setBtn3Content("立即更新")
                    .setOnClickListener3 { dialog, which ->
                        mDialogChooseNormal?.dismiss()
                        showUpdateDialog()
                    }.build()
        }
        mDialogChooseNormal?.show()
    }

    var hintDialog: DialogHintNormal? = null
    fun updateCantCancel() {
        if (hintDialog === null) {
            hintDialog = DialogHintNormal.HintBuilder()
                    .setTitle("发现新版本")
                    .setContent("当前版本过低，为了保障您的正常使用，请及时更新")
                    .setBtn2Content("立即更新")
                    .setCancelable(false)
                    .setContext(this)
                    .setOnClickListener(DialogInterface.OnClickListener { dialog, which ->
                        //                        ToastUtils.showShort("which is" + which + "按钮被点击")
                        hintDialog?.dismiss()
//                        downloadNewApp()
                        showUpdateDialog()
                    })
                    .build()
        }
        hintDialog?.show()
    }

    var mAppUpdateDialog: AppUpdateDialog? = null
    fun showUpdateDialog() {
        if (mCheckUpdateResponse != null && !isEmpty(mCheckUpdateResponse?.downloadUrl)) {
            var decoderUrl = UrlDecoderHelper.decode(mCheckUpdateResponse?.downloadUrl)
            if (mAppUpdateDialog == null) {
                mAppUpdateDialog = AppUpdateDialog(this, decoderUrl)
            }
            mAppUpdateDialog?.show()
            downloadNewApp(decoderUrl)
        }
    }

    fun downloadNewApp(url: String) {
        mPresenter?.downLoadFile(url, "51pingtiao.apk")
    }

    var mCheckUpdateResponse: CheckUpdateResponse? = null
    override fun checkUpdate(data: CheckUpdateResponse?) {
        mCheckUpdateResponse = data
        var handleResult = data?.handleResult
        when (handleResult) {//0：强制更新 1：不强制更新
            "0" -> {
                updateCantCancel()
            }
            "1" -> {
                updateCanCancel()
            }
        }

    }
    override fun downPercent(percent: Int?) {
        runOnUiThread(Runnable {
            mAppUpdateDialog?.setNumberProgressBar(percent!!)
        })
    }

    /**
     * 下载成功后 安装
     */
    override fun downApkSuc(path: String?) {
        AppUpdateHelper.installAPK(this, File(path))
    }

}
