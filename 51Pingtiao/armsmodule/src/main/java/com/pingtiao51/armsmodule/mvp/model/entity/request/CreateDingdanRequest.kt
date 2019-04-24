package com.pingtiao51.armsmodule.mvp.model.entity.request

data class CreateDingdanRequest(
    val appVersion: String?,
    val noteId: Int?,
    val openId: String?,
    val os: String?,
    val payAmount: String?,
    val payMethod: String?,
    val tradeType: String?,
    val userBankId: Int?
)