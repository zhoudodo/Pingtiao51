package com.pingtiao51.armsmodule.mvp.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.jess.arms.di.component.AppComponent
import com.pingtiao51.armsmodule.R
import com.pingtiao51.armsmodule.di.component.DaggerCreateDianziJietiaoComponent
import com.pingtiao51.armsmodule.di.module.CreateDianziJietiaoModule
import com.pingtiao51.armsmodule.mvp.contract.CreateDianziJietiaoContract
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserDetailInfoResponse
import com.pingtiao51.armsmodule.mvp.presenter.CreateDianziJietiaoPresenter
import com.pingtiao51.armsmodule.mvp.ui.helper.PingtiaoConst
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference
import com.zls.baselib.custom.view.dialog.DialogHintNormal
import kotlinx.android.synthetic.main.activity_create_dianzi_jietiao.*


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
class CreateDianziJietiaoActivity : BaseArmsActivity<CreateDianziJietiaoPresenter>(), CreateDianziJietiaoContract.View {


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCreateDianziJietiaoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .createDianziJietiaoModule(CreateDianziJietiaoModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_create_dianzi_jietiao //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.getUserDetailInfo()
    }


    override fun initData(savedInstanceState: Bundle?) {
        setTitle("新建")

        woshijiekuanren.setOnClickListener(View.OnClickListener { it: View? ->
            //我是借款人跳转
            if (mUserDetailInfoResponse === null || (!mUserDetailInfoResponse!!.isVerified!!)) {
                //未实名认证
                showUserVertify();
            } else {
                val intent = Intent(this, XieJietiaoActivity::class.java)
                intent.putExtra(XieJietiaoActivity.XieJietiaoActivity, XieJietiaoActivity.JIEKUANREN)
                startActivity(intent)
            }
        })

        woshichujieren.setOnClickListener(View.OnClickListener { it: View? ->
            // 我是出借人跳转
            if (mUserDetailInfoResponse === null || (!mUserDetailInfoResponse!!.isVerified!!)) {
                //未实名认证
                showUserVertify();
            } else {
                val intent = Intent(this, XieJietiaoActivity::class.java)
                intent.putExtra(XieJietiaoActivity.XieJietiaoActivity, XieJietiaoActivity.CHUJIEREN)
                startActivity(intent)
            }
        })
    }

    var mUserDetailInfoResponse: UserDetailInfoResponse? = null;
    override fun showUserVertifyDialog(rep: UserDetailInfoResponse) {
        mUserDetailInfoResponse = rep
        SavePreference.save(PingtiaoConst.USER_NAME,rep.realname);
        SavePreference.save(PingtiaoConst.USER_PHONE,rep.phone);
        SavePreference.save(PingtiaoConst.USER_ID_CARD,rep.identityNo);
    }


    var hintDialog: DialogHintNormal? = null
    fun showUserVertify() {
        if(hintDialog === null) {
            hintDialog = DialogHintNormal.HintBuilder()
                    .setTitle("提示")
                    .setContent("根据国家法规对电子合同服务实名制\n的要求，您需要进行身份信息完善，\n才能继续使用电子凭条服务。")
                    .setBtn2Content("立即认证")
                    .setContext(this)
                    .setOnClickListener(DialogInterface.OnClickListener { dialog, which ->
//                        ToastUtils.showShort("which is" + which + "按钮被点击")
                        hintDialog?.dismiss()
                        shimingrenzheng()
                    })
                    .build()
        }
        hintDialog?.show()
    }

    fun shimingrenzheng(){
        startAct(ShimingrenzhengActivity::class.java)
    }

}
