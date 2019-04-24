package com.pingtiao51.armsmodule.mvp.model.entity.request

data class AddBankCardRequest(
    val appVersion: String?,
    val bankcardNo: String?,
    val identityNo: String?,
    val name: String?,
    val os: String?,
    val phone: String?,
    val vcode: String?
)