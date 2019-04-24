package com.pingtiao51.armsmodule.mvp.model.entity.request

data class AddDianziShoutiaoRequest(
    val amount: String?,
    val appVersion: String?,
    val borrower: String?,
    val comment: String?,
    val lender: String?,
    val os: String?,
    val repaymentDate: String?,
    val urls: List<String?>?
)