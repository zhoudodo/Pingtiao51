package com.pingtiao51.armsmodule.mvp.model.entity.request

data class CreateReportRequest(
    val appVersion: String?,
    val openId: String?,
    val os: String?,
    val payAmount: Double?,
    val payMethod: String?,
    val reportId: String?,
    val tradeType: String?,
    val userBankId: Int?
)