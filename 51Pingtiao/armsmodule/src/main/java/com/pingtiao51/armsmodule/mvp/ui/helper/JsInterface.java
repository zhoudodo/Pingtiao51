package com.pingtiao51.armsmodule.mvp.ui.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.JavascriptInterface;

import com.authreal.api.AuthBuilder;
import com.authreal.api.OnResultCallListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.mvp.model.entity.response.YhyResponse;
import com.pingtiao51.armsmodule.mvp.ui.activity.BaseWebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziShoutiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.LoginActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.MainActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.MyPingtiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.WebViewActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.WebViewShareActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.H5PayReportDialog;
import com.pingtiao51.armsmodule.mvp.ui.helper.sp.SavePreference;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.List;

import static com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity.PING_TIAO_XIANG_QING;


@SuppressLint("JavascriptInterface")
public class JsInterface {

    public interface Js2JavaInterface {
        public void setTitle(String str);

        public void setRightTitle(String str);

        public void setRightClick(String typeName, String str, String jscode);

        public void showDialog();

        public void loadUrl(String url);

        public void reloadUrl(String url);

    }

    private Js2JavaInterface js2JavaInterface;
    private String token;
    private String uri;

    private FragmentActivity activity;

    public JsInterface(String token) {
        this.token = token;
    }

    public JsInterface(String token, FragmentActivity activity) {
        this.token = token;
        this.activity = activity;
    }

    public JsInterface(String token, Js2JavaInterface lis) {
        this.token = token;
        this.js2JavaInterface = lis;
    }

    public JsInterface(String token, FragmentActivity activity, Js2JavaInterface lis) {
        this.token = token;
        this.activity = activity;
        this.js2JavaInterface = lis;
    }

    @JavascriptInterface
    public String getToken() {
        return SavePreference.getStr(this.activity, PingtiaoConst.KEY_TOKEN);
    }


    @JavascriptInterface
    public void loadUrl(String url) {
        if (js2JavaInterface != null) {
            js2JavaInterface.loadUrl(url);
        }
    }

    @JavascriptInterface
    public void reloadUrl(String url) {
        if (js2JavaInterface != null) {
            js2JavaInterface.reloadUrl(url);
        }
    }


    @JavascriptInterface
    public void setUri(String uri) {
        this.uri = uri;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        activity.startActivity(intent);
    }

    @JavascriptInterface
    public void setTitle(String title) {
        if (js2JavaInterface != null) {
            js2JavaInterface.setTitle(title);
        }
    }

    public final static int RIGHT_TYPE_1 = 1;
    public final static int RIGHT_TYPE_2 = 2;


    @JavascriptInterface
    public void setRightTitle(String rightTitle) {
        if (js2JavaInterface != null) {
            js2JavaInterface.setRightTitle(rightTitle);
        }
    }

    public final static String TEXT = "text";
    public final static String IMAGE = "img";

    @JavascriptInterface
    public void setRightClick(String typeName, String rightTitle, String jscode) {
        if (js2JavaInterface != null) {
            js2JavaInterface.setRightClick(typeName, rightTitle, jscode);
        }
    }


    @JavascriptInterface
    public void showDialog() {
        if (js2JavaInterface != null) {
            js2JavaInterface.showDialog();
        }
    }

    /**
     * 互金支付弹窗
     */
    @JavascriptInterface
    public void showPayReprotDialog(String title, String reportid, double payAmount) {
        ActivityUtils.getTopActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                H5PayReportDialog h5PayReportDialog = new H5PayReportDialog(ActivityUtils.getTopActivity(), title, reportid, payAmount);
                h5PayReportDialog.show();
            }
        });

    }

    /**
     * 回到首页
     */
    @JavascriptInterface
    public void setMainHome() {
        MobclickAgent.onEvent(activity, "webview_fanhuishouye", "借条分享页\t点击“返回首页”");
        List<Activity> activities = ActivityUtils.getActivityList();
        for (Activity act : activities) {
            if (!(act instanceof MainActivity)) {
                act.finish();
            }
        }
        activity.finish();
//        ActivityUtils.startActivity(MainActivity.class);
    }

    /**
     * 回到登录页面
     */
    @JavascriptInterface
    public void setLogin() {
        ActivityUtils.startActivity(LoginActivity.class);
    }

    /**
     * 回到凭条列表
     */
    @JavascriptInterface
    public void setMyPingtiao(String type) {
        MobclickAgent.onEvent(activity, "webview_chakanjietiao", "借条分享页\t点击“查看借条”");
        Bundle bundle = new Bundle();
        bundle.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
        int juese = MyPingtiaoActivity.JIEKUANREN;
        if ("0".equals(type)) {
            juese = MyPingtiaoActivity.JIEKUANREN;
        } else if ("1".equals(type)) {
            juese = MyPingtiaoActivity.CHUJIEREN;
        }
        bundle.putInt(MyPingtiaoActivity.FINISH_CREATE, MyPingtiaoActivity.BACK_FINISH_CREATE);
        bundle.putInt(MyPingtiaoActivity.JUESE, juese);
        ActivityUtils.startActivity(bundle, MyPingtiaoActivity.class);
        activity.finish();
    }

    /**
     * 凭条详情
     */
    @JavascriptInterface
    public void setXiangqing(String type, int id) {
        //凭条类型 OWE_NOTE 电子借条 PAPPER_OWE_NOTE纸质借条 PAPER_RECEIPT_NOTE 纸质收条 RECEIPT_NOTE电子收条
        Bundle bundle = new Bundle();
        bundle.putInt(PING_TIAO_XIANG_QING, (int) id);
        switch (type) {
            case "OWE_NOTE":
                ActivityUtils.finishActivity(DianziJietiaoXiangqingActivity.class);
                ActivityUtils.startActivity(bundle, DianziJietiaoXiangqingActivity.class);
                break;
            case "PAPER_OWE_NOTE":
                ActivityUtils.finishActivity(ZhizhiJietiaoXiangqingActivity.class);
                ActivityUtils.startActivity(bundle, ZhizhiJietiaoXiangqingActivity.class);
                break;
            case "PAPER_RECEIPT_NOTE":
                ActivityUtils.finishActivity(ZhizhiShoutiaoXiangqingActivity.class);
                ActivityUtils.startActivity(bundle, ZhizhiShoutiaoXiangqingActivity.class);
                break;
            case "RECEIPT_NOTE":
                ActivityUtils.finishActivity(DianziShoutiaoXiangqingActivity.class);
                ActivityUtils.startActivity(bundle, DianziShoutiaoXiangqingActivity.class);
                break;
            default:
                break;
        }
    }

    private final static String AUTH_KEY = "b240ecac-d1a6-424d-95cf-9f79cc75adee";
    private final static String SECRET_KEY = "6eb14834-749a-4c53-96a0-c3af53ae18c1";

    @JavascriptInterface
    public void faceAuth(String id, String urlNotify, String tiaozhuanUrl) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AuthBuilder mAuthBuilder = new AuthBuilder(id, AUTH_KEY, SECRET_KEY, urlNotify, new OnResultCallListener() {
                    @Override
                    public void onResultCall(String s, JSONObject object) {
                        YhyResponse yhyResponse = ArmsUtils.obtainAppComponentFromContext(activity).gson().fromJson(s, YhyResponse.class);
                        if ("T".equals(yhyResponse.getResult_auth())) {
                            Bundle bundle = new Bundle();
                            bundle.putString(BaseWebViewActivity.WEBVIEW_TITLE, "认证成功");
                            bundle.putString(BaseWebViewActivity.WEBVIEW_URL, tiaozhuanUrl);
                            ActivityUtils.startActivity(bundle, WebViewActivity.class);
                            activity.finish();
                        }
                    }

                });
                //下文调用方法做为范例，请以对接文档中的调用方法为准
                mAuthBuilder.faceAuth(activity);
            }
        });
    }

    /**
     * 关闭当前页面
     */
    @JavascriptInterface
    public void finishAct() {
        activity.finish();
    }

}
