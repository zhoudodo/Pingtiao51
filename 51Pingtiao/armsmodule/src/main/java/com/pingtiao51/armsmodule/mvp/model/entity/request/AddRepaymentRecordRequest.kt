package com.pingtiao51.armsmodule.mvp.model.entity.request

data class AddRepaymentRecordRequest(
    val amount: Double?,
    val appVersion: String?,
    val noteId: Int?,
    val os: String?,
    val payee: String?,
    val payer: String?,
    val repaymentWay: String?,
    val urls: List<String?>?
)