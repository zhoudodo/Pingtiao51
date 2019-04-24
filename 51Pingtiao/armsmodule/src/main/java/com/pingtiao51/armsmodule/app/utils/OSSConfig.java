package com.pingtiao51.armsmodule.app.utils;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;

/**
 * 创建OSS对象的OSSCredentialProvider
 */
public class OSSConfig {

    public static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//    public static final String accessKeyId = "LTAIewWJxUanPizg";
//    public static final String accessKeySecret = "ZwU3QQW5Rn24EgDn8ypPWzP0i2b3Yd";
//    public static final String bucketName = "pingtiao-dev";
    // Access Key id 问后台要
    public static final String AK = "LTAIEingPb7pl5xV";// LTAIewWJxUanPizg
    // SecretKeyId 问后台要
    public static final String SK = "SX6br2Gw8OfzqVvd6SMfzZOE20OK2G";// ZwU3QQW5Rn24EgDn8ypPWzP0i2b3Yd



    public static OSSCredentialProvider newCustomSignerCredentialProvider() {
        return new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                return OSSUtils.sign(AK, SK, content);
            }
        };
    }


}