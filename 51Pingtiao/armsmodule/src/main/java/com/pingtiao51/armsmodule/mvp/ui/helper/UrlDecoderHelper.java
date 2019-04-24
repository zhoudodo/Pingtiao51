package com.pingtiao51.armsmodule.mvp.ui.helper;

import com.jess.arms.utils.UrlEncoderUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlDecoderHelper {
    public static String decode(String input) {
        if(input == null){
            return null;
        }
        String item = input;
        if (UrlEncoderUtils.hasUrlEncoded(item)) {
            try {
                item = URLDecoder.decode(item, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return item;
    }
}
