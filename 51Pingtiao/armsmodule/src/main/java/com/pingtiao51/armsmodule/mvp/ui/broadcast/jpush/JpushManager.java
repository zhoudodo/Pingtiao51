package com.pingtiao51.armsmodule.mvp.ui.broadcast.jpush;

import com.blankj.utilcode.util.ActivityUtils;
import com.pingtiao51.armsmodule.BuildConfig;

import cn.jpush.android.api.JPushInterface;

public class JpushManager {

    public static void setAlias(String phoneNum){
        String prefix = BuildConfig.DEBUG ? "debug":"";
        phoneNum = phoneNum + prefix;
        JPushInterface.setAlias(ActivityUtils.getTopActivity(),0,phoneNum);
    }

}
