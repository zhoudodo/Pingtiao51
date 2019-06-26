package com.pingtiao51.armsmodule.mvp.model.entity.request

data class NewsInfoRequest(
    val category: String?,
    val page: Int?,
    val showInBanner: Int?,
    val size: Int?
)