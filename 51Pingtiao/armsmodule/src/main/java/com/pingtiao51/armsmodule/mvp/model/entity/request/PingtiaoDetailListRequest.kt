package com.pingtiao51.armsmodule.mvp.model.entity.request

data class PingtiaoDetailListRequest(
    val appVersion: String?,
    val enoteType: String?,
    val os: String?,
    val page: Int?,
    val queryName: String?,
    val queryScopeType: String?,
    val size: Int?,
    val sortType: String?,
    val userRoleType: String?
)