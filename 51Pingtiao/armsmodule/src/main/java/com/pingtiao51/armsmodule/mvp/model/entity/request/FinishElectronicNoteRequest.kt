package com.pingtiao51.armsmodule.mvp.model.entity.request

data class FinishElectronicNoteRequest(
    val appVersion: String?,
    val id: Long?,
    val os: String?,
    val queryRequestType: String?//1:借入 2：出借
)