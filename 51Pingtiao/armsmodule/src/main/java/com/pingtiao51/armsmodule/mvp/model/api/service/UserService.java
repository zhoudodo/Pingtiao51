package com.pingtiao51.armsmodule.mvp.model.api.service;

import com.pingtiao51.armsmodule.mvp.model.entity.request.CarrierAuthRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CodeLoginRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ResetPswRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.SendCodeRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.UnBindCardRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.UserDetailInfoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.UserHeaderSettingRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.WxBindPhoneRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.WxLoginRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.LoginResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserDetailInfoResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.WxBindPhoneResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.WxLoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {


    /**
     * 短信验证
     * @param request
     * @return
     */
    @POST("pingtiao/sendVcode")
    Observable<BaseJson<Object>> sendCodeRequest(@Body SendCodeRequest request);

    /**
     * 登录
     * @param request
     * @return
     */
    @POST("pingtiao/login")
    Observable<BaseJson<LoginResponse>> codeLoginRequest(@Body CodeLoginRequest request);

    /**
     * 重置密码
     * @param request
     * @return
     */
    @POST("pingtiao/resetLoginPassword")
    Observable<BaseJson<Object>> resetLoginPassword(@Body ResetPswRequest request);

   /**
     * 微信登录
     * @param request
     * @return
     */
    @POST("pingtiao/wxLogin")
    Observable<BaseJson<WxLoginResponse>> wxLogin(@Body WxLoginRequest request);

   /**
     * 微信绑定手机号
     * @param request
     * @return
     */
    @POST("pingtiao/wxLoginBindingPhone")
    Observable<BaseJson<WxBindPhoneResponse>> wxLoginBindingPhone(@Body WxBindPhoneRequest request);



    /**
     * 获取用户详情
     * @param request
     * @return
     */
    @POST("pingtiao/user/auth/summary")
    Observable<BaseJson<UserDetailInfoResponse>> getUserDetailInfos(@Body UserDetailInfoRequest request);


    /**
     * 运营商三要素认证 传姓名、身份证号、手机号、验证码去掉了
     * @param request
     * @return
     */
    @POST("pingtiao/auth/carrierAuth")
    Observable<BaseJson<Object>> getCarrierAuth(@Body CarrierAuthRequest request);

    /**
     * 运营商三要素认证 传姓名、身份证号、手机号、验证码去掉了
     * @param request
     * @return
     */
    @POST("pingtiao/auth/unbindBankCard")
    Observable<BaseJson<Object>> unbindBankCard(@Body UnBindCardRequest request);

    /**
     * 设置/修改头像
     * @param request
     * @return
     */
    @POST("pingtiao/user/auth/userHeadSetting")
    Observable<BaseJson<Object>> userHeadSetting(@Body UserHeaderSettingRequest request);
}
