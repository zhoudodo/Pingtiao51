package com.pingtiao51.armsmodule.mvp.ui.helper.umeng;

import android.app.Activity;

import com.pingtiao51.armsmodule.app.MyApplication;
import com.pingtiao51.armsmodule.mvp.ui.activity.CreateDianziJietiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.CreateDianziShoutiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.CreateJietiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.JietiaoMobanVpActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.LoginActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.MainActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.SecureCopyActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ShimingrenzhengActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ShoutiaoMobanVpActivityActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.SplashActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.WebViewShareActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.XieJietiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.XieShoutiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.YulanJietiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiJietiaoMainActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoMainActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * 利用Umeng统计计数事件
 */
public class UmengEventReport {

//InSplashActivity, 启动页进入启动页, 1
//woyaobeifenpingtiao, 备份点击我要备份凭条, 1
//InMainActivity, 首页进入首页, 1
//woyaoxiepingtiao, 首页点击我要写凭条, 1
//InCreateJietiaoActivity, 新建页进入新建页, 1
//OutCreateJietiaoActivity, 新建页点击返回, 1
//InCreateDianziJietiaoActivity, 新建页点击我要写借条, 1
//InCreateDianziShoutiaoActivity, 新建页点击我要写收条, 1
//OutCreateDianziJietiaoActivity, 新建电子借条页点击返回, 1
//woshijiekuanren, 新建电子借条页点击我是借款人, 1
//woshichujieren, 新建电子借条页点击我是出借人, 1
//OutCreateDianziShoutiaoActivity, 新建电子收条点击返回, 1
//xieshoutiao, 新建电子收条点击写收条, 1
//OutXieJietiaoActivity, 写电子借条点击返回, 1
//dianzijietiaoyulan, 写电子借条点击预览, 1
//shengchengjietiao, 写电子借条点击生成借条, 1
//fasongduifang, 借条分享页点击发送或发送对方, 1
//fenxiangweixin, 借条分享页选择分享至微信好友, 1
//fenxiangerweima, 借条分享页选择二维码分享, 1
//fenxianglianjie, 借条分享页选择复制链接, 1
//fenxiangduanxin, 借条分享页选择发送短信, 1
//webviewfanhuishouye, 借条分享页点击返回首页, 1
//webviewchakanjietiao, 借条分享页点击查看借条, 1
//OutWebViewShareActivity, 借条分享页点击左上角返回, 1
//InXieShoutiaoActivity, 写电子收条进入写电子收条页, 1
//dianzishoutiaoluyan, 写电子收条点击预览, 1
//shengchengshoutiao, 写电子收条点击生成收条, 1
//InShimingrenzhengActivity, 实名认证页进入实名认证页, 1
//shimingrenzhengtijiao, 实名认证页点击提交, 1
//shouyeleft, 首页点击左上角, 1
//shouyexiaoxi, 首页点击消息, 1
//shouyexinshoubangzhu, 首页点击新手帮助, 1
//InLoginActivity, 登录页进入登录页, 1
//codehuoquyanzhenma, 登录页点击验证码登录的获取验证码, 1
//mimadenglu, 登录页点击密码登录的登录, 1
//yanzhengmadenglu, 登录页点击验证码登录的登录, 1
//codewangjimima, 登录页点击忘记密码, 1
//psdhuoquyanzhenma, 登录页点击忘记密码的获取验证码, 1
//wangjimimadenglu, 登录页点击忘记密码的登录, 1
//yanzhengmafanhui, 登录页点击验证码登录的返回, 1
//chanxinyong, 首页点击-查信用, 1
//qiujiekuan, 首页点击求借款, 1
//jietiaomoban, 首页点击借条模板, 1
//shoutiaomoban, 首页点击收条模板, 1
//pingtiaoleixing, 首页点击选择凭条类型小三角那块, 1
//InZhizhiJietiaoMainActivity, 备份凭条页点击纸质借条, 1
//InZhizhiShoutiaoMainActivity, 备份凭条页点击纸质收条, 1
//OutSecureCopyActivity, 备份凭条页点击返回, 1
//InJietiaoMobanVpActivity, 备份借条页点击模板, 1
//OutZhizhiJietiaoMainActivity, 备份借条页点击返回, 1
//beifenjietiaosave, 备份借条页点击保存, 1
//InShoutiaoMobanVpActivityActivity, 备份收条页点击模板, 1
//OutZhizhiShoutiaoMainActivity, 备份收条页点击返回, 1
//beifenshoutiaosave, 备份收条页点击保存, 1





    public final static String PREFIX_IN = "In";//进入某activity

    public static void interceptCreateActivity(Activity activity) {
        if (activity instanceof SplashActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "SplashActivity", "启动页\t进入启动页");
        } else if (activity instanceof MainActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "MainActivity", " 首页\t进入首页");
        } else if (activity instanceof CreateJietiaoActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "CreateJietiaoActivity", "新建页\t进入新建页");
        } else if (activity instanceof CreateDianziJietiaoActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "CreateDianziJietiaoActivity", "新建页\t点击“我要写借条”");
        } else if (activity instanceof CreateDianziShoutiaoActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "CreateDianziShoutiaoActivity", "新建页\t点击“我要写收条”");
        } else if (activity instanceof XieShoutiaoActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "XieShoutiaoActivity", "写电子收条\t进入写电子收条页");
        } else if (activity instanceof ShimingrenzhengActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "ShimingrenzhengActivity", "实名认证页\t进入实名认证页");
        } else if (activity instanceof LoginActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "LoginActivity", "登录页\t进入登录页");
        } else if (activity instanceof ZhizhiJietiaoMainActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "ZhizhiJietiaoMainActivity", "备份凭条页\t点击“纸质借条”");
        } else if (activity instanceof ZhizhiShoutiaoMainActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "ZhizhiShoutiaoMainActivity", "备份凭条页\t点击“纸质收条”");
        } else if (activity instanceof JietiaoMobanVpActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "JietiaoMobanVpActivity", "备份借条页\t点击“模板”");
        } else if (activity instanceof ShoutiaoMobanVpActivityActivity) {
            MobclickAgent.onEvent(activity, PREFIX_IN + "ShoutiaoMobanVpActivityActivity", "备份收条页\t点击“模板”");
        }

    }

    public final static String PREFIX_OUT = "Out";//进入某activity

    public static void interceptDestoryActivity(Activity activity) {
        if (activity instanceof CreateJietiaoActivity) {
            MobclickAgent.onEvent(activity, PREFIX_OUT + "CreateJietiaoActivity", "新建页\t点击“返回”");
        } else if (activity instanceof CreateDianziJietiaoActivity) {
            MobclickAgent.onEvent(activity, PREFIX_OUT + "CreateDianziJietiaoActivity", "新建电子借条页\t点击“返回”");
        } else if (activity instanceof CreateDianziShoutiaoActivity) {
            MobclickAgent.onEvent(activity, PREFIX_OUT + "CreateDianziShoutiaoActivity", "新建电子收条\t点击“返回”");
        } else if (activity instanceof XieJietiaoActivity) {
            MobclickAgent.onEvent(activity, PREFIX_OUT + "XieJietiaoActivity", "写电子借条\t点击“返回”");
        } else if (activity instanceof WebViewShareActivity) {
            MobclickAgent.onEvent(activity, PREFIX_OUT + "WebViewShareActivity", "借条分享页\t点击左上角“返回”");
        } else if (activity instanceof SecureCopyActivity) {
            MobclickAgent.onEvent(activity, PREFIX_OUT + "SecureCopyActivity", "备份凭条页\t点击“返回”");
        } else if (activity instanceof ZhizhiJietiaoMainActivity) {
            MobclickAgent.onEvent(activity, PREFIX_OUT + "ZhizhiJietiaoMainActivity", "备份借条页\t点击“返回”");
        } else if (activity instanceof ZhizhiShoutiaoMainActivity) {
            MobclickAgent.onEvent(activity, PREFIX_OUT + "ZhizhiShoutiaoMainActivity", "备份收条页\t点击“返回”");
        }

    }

}
