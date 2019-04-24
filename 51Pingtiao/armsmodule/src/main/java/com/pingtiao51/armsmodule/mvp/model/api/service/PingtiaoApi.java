package com.pingtiao51.armsmodule.mvp.model.api.service;

import com.pingtiao51.armsmodule.mvp.model.entity.request.AddDianziJietiaoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.AddDianziShoutiaoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.AuthSignRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.CloseElectronicNoteRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.FinishElectronicNoteRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.ModifyPingtiaoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PingtiaoDetailListRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PingtiaoShareRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.PingtiaoXiangqingRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.beifenZhizhiJietiaoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.request.beifenZhizhiShoutiaoRequest;
import com.pingtiao51.armsmodule.mvp.model.entity.response.BaseJson;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoDetailListResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoShareResponse;
import com.pingtiao51.armsmodule.mvp.model.entity.response.PingtiaoXiangqingResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface PingtiaoApi {


    /**
     * 添加电子借条
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/addIou")
    Observable<BaseJson<Object>> addDianziJietiao(@Body AddDianziJietiaoRequest request);

    /**
     * 添加电子收条
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/addReceipt")
    Observable<BaseJson<String>> addDianziShoutiaotiao(@Body AddDianziShoutiaoRequest request);

    /**
     * 备份纸质借条
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/backupIou")
    Observable<BaseJson<Object>> beifenZhizhiJietiao(@Body beifenZhizhiJietiaoRequest request);

    /**
     * 备份纸质收条
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/backupReceipt")
    Observable<BaseJson<Object>> beifenZhizhiShoutiao(@Body beifenZhizhiShoutiaoRequest request);

    /**
     * 获得用户凭条列表
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/listPagedElectronicNotes")
    Observable<BaseJson<PingtiaoDetailListResponse>> getPingtiaoListDetails(@Body PingtiaoDetailListRequest request);

    /**
     * 根据ID查询电子凭条详情
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/electronicNoteDetail")
    Observable<BaseJson<PingtiaoXiangqingResponse>> getPingtiaoById(@Body PingtiaoXiangqingRequest request);

    /**
     * 修改凭条
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/editNote")
    Observable<BaseJson<Object>> modifyPingtiao(@Body ModifyPingtiaoRequest request);

    /**
     * 设置电子凭条为已还款
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/finishElectronicNote")
    Observable<BaseJson<Object>> finishElectronicNote(@Body FinishElectronicNoteRequest request);

    /**
     * 关闭电子凭条
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/closeElectronicNote")
    Observable<BaseJson<Object>> closeElectronicNote(@Body CloseElectronicNoteRequest request);

    /**
     * 手动签章
     *
     * @param request
     * @return
     */
    @POST("pingtiao/econtract/auth/extSign")
    Observable<BaseJson<Object>> extSign(@Body AuthSignRequest request);

    /**
     * 获取凭条分享内容
     *
     * @param request
     * @return
     */
    @POST("pingtiao/note/auth/noteShareInfo")
    Observable<BaseJson<PingtiaoShareResponse>> noteShareInfo(@Body PingtiaoShareRequest request);

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);
}