package com.pingtiao51.armsmodule.mvp.model.entity.request

data class CodeLoginRequest(
    val appVersion: String?=null,
    val deviceId: String ?=null,
    val lbsArea: String?=null,
    val lbsDetail: String ? = null,
    val os: String?=null,
    val password: String?=null,
    val phone: Long?=null,
    val phoneModel: String?=null,
    val source: String?=null,
    val vcode: String?=null
)