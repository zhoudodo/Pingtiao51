package com.pingtiao51.armsmodule.mvp.model.api.service;

import com.pingtiao51.armsmodule.mvp.model.entity.request.AddBankCardRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CommonRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CreateDingdanRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CreateReportRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PayDingdanRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ProductPriceRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.CreateDingdanResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.ProductPriceResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.SupportPaymentsResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.UserBankListResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PayApi {

    /**
     * 获取订单价格
     *
     * @param request
     * @return
     */
    @POST("pingtiao/common/productPrice")
    Observable<BaseJson<ProductPriceResponse>> productPrice(@Body ProductPriceRequest request);

    /**
     * 查询用户绑定的银行卡列表
     *
     * @param request
     * @return
     */
    @POST("pingtiao/auth/bindedBankCards")
    Observable<BaseJson<List<UserBankListResponse>>> getUserBankList(@Body CommonRequest request);
    /**
     * 绑定银行卡
     *
     * @param request
     * @return
     */
    @POST("pingtiao/auth/bindBankCard")
    Observable<BaseJson<Object>> bindBankCard(@Body AddBankCardRequest request);
    /**
     * 发起生成支付订单  凭条
     *
     * @param request
     * @return
     */
    @POST("pingtiao/auth/pay/applyOrder")
    Observable<BaseJson<CreateDingdanResponse>> createDingdan(@Body CreateDingdanRequest request);
    /**
     * 发起生成支付订单  互金报告
     *
     * @param request
     * @return
     */
    @POST("pingtiao/auth/pay/applyReportOrder")
    Observable<BaseJson<CreateDingdanResponse>> createApplyReportDingdan(@Body CreateReportRequest request);
    /**
     * 获取支持的支付方式
     *
     * @param request
     * @return
     */
    @POST("pingtiao/common/supportPayments")
    Observable<BaseJson<SupportPaymentsResponse>> supportPayments(@Body CommonRequest request);

    /**
     * 验证码确认支付订单
     *
     * @param request
     * @return
     */
    @POST("pingtiao/auth/pay/confirmOrder")
    Observable<BaseJson<Object>> confirmOrder(@Body PayDingdanRequest request);


}
