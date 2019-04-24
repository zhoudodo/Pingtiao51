package com.pingtiao51.armsmodule.mvp.model.entity.request

data class PayDingdanRequest(
    val appVersion: String?,
    val confirmVcode: String?,
    val orderNo: String?,
    val os: String?
)