package com.pingtiao51.armsmodule.mvp.ui.helper.webview;

import android.app.Activity;
import android.util.Log;

import java.io.File;

public class WebviewUtils {
    public final static String TAG = "WebviewUtils";


    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private static final String APP_WEBVIEW_CACAHE_DIRNAME = "/webviewCache";
    /**
     * 清除WebView缓存
     */
    public static void clearWebViewCache(Activity activity){
        //清理Webview缓存数据库
        try {
            activity.deleteDatabase("webview.db");
            activity.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(activity.getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
//        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(activity.getCacheDir().getAbsolutePath()+APP_WEBVIEW_CACAHE_DIRNAME);
//        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }


    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        Log.i(TAG, "delete file path=" + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

}
