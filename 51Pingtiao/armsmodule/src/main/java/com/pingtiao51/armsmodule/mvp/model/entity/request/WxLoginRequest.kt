package com.pingtiao51.armsmodule.mvp.model.entity.request

data class WxLoginRequest(
    val appVersion: String?,
    val code: String?,
    val deviceId: String?,
    val lbsArea: String?,
    val lbsDetail: String?,
    val os: String?,
    val phoneModel: String?,
    val source: String?
)