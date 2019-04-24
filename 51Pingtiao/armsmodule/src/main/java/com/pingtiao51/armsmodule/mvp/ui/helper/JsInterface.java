package com.pingtiao51.armsmodule.mvp.ui.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.ActivityUtils;
import com.jess.arms.utils.ArmsUtils;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.DianziShoutiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.LoginActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.MainActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.MyPingtiaoActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiJietiaoXiangqingActivity;
import com.pingtiao51.armsmodule.mvp.ui.activity.ZhizhiShoutiaoXiangqingActivity;

import java.util.List;

import static com.pingtiao51.armsmodule.mvp.ui.activity.DianziJietiaoXiangqingActivity.PING_TIAO_XIANG_QING;


@SuppressLint("JavascriptInterface")
public class JsInterface {

    public interface  Js2JavaInterface{
        public void setTitle(String str);
        public void setRightTitle(String str);
        public void showDialog();

    }

    private Js2JavaInterface js2JavaInterface;
    private String token;
    private String uri;

    private FragmentActivity activity;

    public JsInterface(String token){
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
    public JsInterface(String token,FragmentActivity activity, Js2JavaInterface lis) {
        this.token = token;
        this.activity = activity;
        this.js2JavaInterface = lis;
    }

    @JavascriptInterface
    public String getToken() {
        return token;
    }




    @JavascriptInterface
    public void setUri(String uri) {
        this.uri = uri;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        activity.startActivity(intent);
    }

    @JavascriptInterface
    public void setTitle(String title) {
        if(js2JavaInterface != null){
            js2JavaInterface.setTitle(title);
        }
    }

    @JavascriptInterface
    public void setRightTitle(String rightTitle) {
        if(js2JavaInterface != null){
            js2JavaInterface.setRightTitle(rightTitle);
        }
    }
    @JavascriptInterface
    public void showDialog() {
        if(js2JavaInterface != null){
            js2JavaInterface.showDialog();
        }
    }

    /**
     * 回到首页
     */
    @JavascriptInterface
    public void setMainHome() {
        List<Activity>  activities = ActivityUtils.getActivityList();
        for(Activity act:activities){
            if(!(act instanceof MainActivity)){
                act.finish();
            }
        }
        activity.finish();
//        ActivityUtils.startActivity(MainActivity.class);
    }
    /**
     * 回到首页
     */
    @JavascriptInterface
    public void setLogin() {
        ActivityUtils.startActivity(LoginActivity.class);
    }
    /**
     * 回到凭条列表
     */
    @JavascriptInterface
    public void setMyPingtiao() {
        Bundle bundle = new Bundle();
        bundle.putInt(MyPingtiaoActivity.TAG, MyPingtiaoActivity.DIAN_ZI);
        bundle.putInt(MyPingtiaoActivity.FINISH_CREATE, MyPingtiaoActivity.BACK_FINISH_CREATE);
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
        bundle.putInt(PING_TIAO_XIANG_QING, (int)id);
        switch (type){
            case "OWE_NOTE":
                ActivityUtils.startActivity(bundle,DianziJietiaoXiangqingActivity.class);
                break;
            case "PAPER_OWE_NOTE":
                ActivityUtils.startActivity(bundle,ZhizhiJietiaoXiangqingActivity.class);
                break;
            case "PAPER_RECEIPT_NOTE":
                ActivityUtils.startActivity(bundle,ZhizhiShoutiaoXiangqingActivity.class);
                break;
            case "RECEIPT_NOTE":
                ActivityUtils.startActivity(bundle,DianziShoutiaoXiangqingActivity.class);
                break;
            default:
                break;
        }
    }
}
