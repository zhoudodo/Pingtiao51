package com.pingtiao51.armsmodule.mvp.ui.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.blankj.utilcode.util.ActivityUtils;

import java.io.File;

public class AppUpdateHelper {

    /**
     * 安装app
     *
     * @param context
     * @param savedFile
     * @return
     */
    ////调用系统的安装方法
    public static void installAPK(Context context,File savedFile) {
        //调用系统的安装方法
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(context, "com.receipt.px.fileprovider", savedFile);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(savedFile);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        ActivityUtils.getTopActivity().startActivity(intent);
//        ActivityUtils.finishAllActivities();
    }
}
