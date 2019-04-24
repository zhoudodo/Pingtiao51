package com.pingtiao51.armsmodule.mvp.model.entity.request

data class AddDianziJietiaoRequest(
    val amount: String?,
    val appVersion: String?,
    val borrower: String?,
    val identityNo: String?,
    val lender: String?,
    val loanDate: String?,
    val loanUsage: String?,
    val os: String?,
    val payer: String?,
    val repaymentDate: String?,
    val userId: Long?,
    val userType: String?,
    val yearRate: String?
)