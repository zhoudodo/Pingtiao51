package com.pingtiao51.armsmodule.mvp.model.entity.request

data class PingTiaoSeachRequest(
    val appVersion: String?,
    val bigNoteType: String?,//凭条大类别 ELECTRONIC 电子 PAPPER 纸质
    val os: String?
)