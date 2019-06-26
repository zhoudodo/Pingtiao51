package com.pingtiao51.armsmodule.mvp.model.entity.request

data class WxBindPhoneRequest(
    val appVersion: String?,
    val code: String?,
    val deviceId: String?,
    val lbsArea: String?,
    val lbsDetail: String?,
    val os: String?,
    val phone: String?,
    val phoneModel: String?,
    val source: String?,
    val vcode: String?
)