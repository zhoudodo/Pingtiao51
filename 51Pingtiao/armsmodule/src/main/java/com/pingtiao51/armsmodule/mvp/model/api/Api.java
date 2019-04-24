/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pingtiao51.armsmodule.mvp.model.api;


import com.pingtiao51.armsmodule.BuildConfig;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by JessYan on 08/05/2016 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {
    String WECHAT_APPKEY = "wxcdf3efdd7570a44a";//微信appkey
    String RequestSuccess = "0";
    String tokenValid = "1001";
    String TRACK_BASE_URL = BuildConfig.TRACK_BASE_URL;
    String BASE_URL = BuildConfig.BASE_URL;
    String MOXIE_API_KEY = BuildConfig.MOXIE_API_KEY;
    String BASE_H5_URL = BuildConfig.BASE_H5_URL;
    String ANDROID_BANNER_NID = BuildConfig.ANDROID_BANNER_NID;
    String IMAGE_URL = BASE_URL + "common/homePage/article/baseImage";
    String LVSHU_TOKEN = "wjl_uNzB8YC6ArVRzYYv";

    /**
     * H5界面
     */
    //消息
    String MESSAGE = "message";
    //    合同
    String CONTRACT = "contract";
    //    消费明细
    String TRANSACTION_DETAIL = "transactionDetail";
    //    帮助与反馈
    String CONTACT = "contact";
    //    意见反馈
    String FEEDBACK = "feedback";
    //    分享
    String SHARE_PINGTIAO = "borrowShare?id=0&userType=0";

    String REG_AGREEMENTOK = "regAgreement";

    String XINSHOU_GUIDE = "guide";


    /**
     * appId
     */
    int APPID = BuildConfig.APP_ID;

    String APP_NAME = BuildConfig.APP_NAME;


}
