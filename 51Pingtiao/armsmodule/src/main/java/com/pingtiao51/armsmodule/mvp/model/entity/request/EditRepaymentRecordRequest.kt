package com.pingtiao51.armsmodule.mvp.model.entity.request

data class EditRepaymentRecordRequest(
    val amount: Double?,
    val appVersion: String?,
    val cancellationReason: String?,
    val noteId: Int?,
    val operationType: String?,
    val os: String?
)