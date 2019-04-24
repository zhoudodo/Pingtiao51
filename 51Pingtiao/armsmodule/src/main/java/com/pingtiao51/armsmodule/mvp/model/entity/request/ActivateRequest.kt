package com.pingtiao51.armsmodule.mvp.model.entity.request



/**
 * 激活的请求体
 * @author fang.xc@outlook.com
 * @date 2018/9/19
 */

data class ActivateRequest(val deviceId: String? = null,
                           val marketSource: String? = null,
                           val androidid: String? = null,
                           val mac: String? = null,
                           val imei: String? = null,
                           val os: String? = null
                           )