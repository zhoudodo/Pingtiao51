package com.pingtiao51.armsmodule.mvp.ui.helper;

public class FileUtil {

    /**
     * 文件路径 去掉反斜杠  /storage/emulated/0/MagazineUnlock/magazine-unlock-hi3955503.jpg 变成  storage/emulated/0/MagazineUnlock/magazine-unlock-hi3955503.jpg
     */
    public static String convertFilePath(String path){
        return path.substring(1,path.length());
    }
}
