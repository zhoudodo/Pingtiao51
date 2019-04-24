package com.pingtiao51.armsmodule.app;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AppCrashHandler implements Thread.UncaughtExceptionHandler {

    private static AppCrashHandler INSTANCE;
    private static Context mContext;


    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");


    private AppCrashHandler() {

    }

    public static AppCrashHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (AppCrashHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppCrashHandler();
                }
            }
        }
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        //保存一份系统默认的CrashHandler
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //使用我们自定义的异常处理器替换程序默认的
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * @param t 出现未捕获异常的线程
     * @param e 未捕获的异常，有了这个ex，我们就可以得到异常信息
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!catchCrashException(e) && mDefaultHandler != null) {
            //没有自定义的CrashHandler的时候就调用系统默认的异常处理方式
            mDefaultHandler.uncaughtException(t, e);
        } else {
            //退出应用
            killProcess();
        }
    }

    private void killProcess() {
        ActivityUtils.finishAllActivities();
        Intent i = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }






    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 未捕获的异常
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean catchCrashException(Throwable ex) {
        Log.d("dodoCrashInfo", "输出错误信息");
        ex.printStackTrace();
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
//                    Toast.makeText(Utils.getApp(), "Duang~~崩啦~~崩啦~~~~", Toast.LENGTH_SHORT).show();
                Toast.makeText(Utils.getApp(), "程序异常，尝试重启", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
//            collectInfo(ex);
        //保存日志文件
//            saveCrashInfo2File();
        //上传崩溃信息
//            uploadCrashMessage(totalInfo);

        return true;
    }


}

