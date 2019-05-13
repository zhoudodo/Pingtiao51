package com.pingtiao51.armsmodule.mvp.model.entity.request

data class UserHeaderSettingRequest(
    val appVersion: String?,
    val os: String?,
    val urls: List<String?>?
)