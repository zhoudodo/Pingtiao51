package com.pingtiao51.armsmodule.mvp.ui.broadcast.jpush;

import com.blankj.utilcode.util.ActivityUtils;

import cn.jpush.android.api.JPushInterface;

public class JpushManager {

    public static void setAlias(String phoneNum){
        JPushInterface.setAlias(ActivityUtils.getTopActivity(),0,phoneNum);
    }

}
