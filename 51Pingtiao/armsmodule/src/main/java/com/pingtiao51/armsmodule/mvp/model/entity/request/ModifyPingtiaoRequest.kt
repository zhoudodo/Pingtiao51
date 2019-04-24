package com.pingtiao51.armsmodule.mvp.model.entity.request

data class ModifyPingtiaoRequest(
    val appVersion: String?,
    val noteId: Long?,
    val os: String?,
    val urls: List<String?>?
)