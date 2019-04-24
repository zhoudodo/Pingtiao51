package com.pingtiao51.armsmodule.mvp.model.entity.request

data class AuthSignRequest(
    val appVersion: String?,
    val noteId: Int?,
    val os: String?,
    val signName: String?,
    val returnUrl: String?
)