package com.pingtiao51.armsmodule.mvp.model.api.service;

import com.pingtiao51.armsmodule.app.MyApplication;
import com.pingtiao51.armsmodule.mvp.model.entity.request.AllMessageStatisticsRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CheckUpdateRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.HomeBannerRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.NewsDetailRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.NewsInfoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PingTiaoSeachRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ResetPswRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ShouHuanKuanRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.CheckUpdateResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomeBannerResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomeMessageScrollResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.HomePageComResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.NewsDetailInfoResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.NewsInfoResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingTiaoSeachResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.ShouHuanKuanResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface HomeApi {

    /**
     * 获取用户收还款
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/queryElectronicNoteInfo")
    Observable<BaseJson<ShouHuanKuanResponse>> getShouHuanKuan(@Body ShouHuanKuanRequest request);

    /**
     * 首页banner获取
     *
     * @param request
     * @return
     */
    @POST("pingtiao/common/banners")
    Observable<BaseJson<List<HomeBannerResponse>>> getHomeBanners(@Body HomeBannerRequest request);

    /**
     * 查询最近的5条凭条
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/lastestNotes")
    Observable<BaseJson<List<PingTiaoSeachResponse>>> getPingTiaoSearch(@Body PingTiaoSeachRequest request);

    /**
     * 统计各个类别未读消息的数量复制
     *
     * @param request
     * @return
     */
    @POST("pingtiao/auth/message/statisticsMessageUnreadByType")
    Observable<BaseJson<Object>> allMessageStaticsReq(@Body AllMessageStatisticsRequest request);

    /**
     * 获取滚动消息提示栏
     *
     * @param
     * @return
     */
    @GET("pingtiao/message/listScrollingMsgs")
    Observable<BaseJson<List<HomeMessageScrollResponse>>> getHomeMessageScroll();

    /**
     * 获得功能入口
     *
     * @param
     * @return
     */
    @GET("pingtiao/common/functionEntries")
    Observable<BaseJson<List<HomePageComResponse>>> getFunctions(@Query("appVersion") String appVersion, @Query("source") String source, @Query("os") String os);

    /**
     * 获取版本信息
     *
     * @param
     * @return
     */
    @POST("pingtiao/common/appVersion")
    Observable<BaseJson<CheckUpdateResponse>> checkUpdate(@Body CheckUpdateRequest request);

    /**
     * 获取banner或者是咨询列表
     *
     * @param
     * @return
     */
    @Headers({"Domain-Name: " + MyApplication.BASE_URL2})
    @POST("gateway/article/mobile/list")
    Observable<BaseJson<NewsInfoResponse>> getNewsInfo(@Body NewsInfoRequest request);

    /**
     * 获取banner或者是咨询列表
     *
     * @param
     * @return
     */
    @Headers({"Domain-Name: " + MyApplication.BASE_URL2})
    @POST("gateway/article/mobile/detail")
    Observable<BaseJson<NewsDetailInfoResponse>> getNewsDetail(@Body NewsDetailRequest request);


}
