package com.pingtiao51.armsmodule.mvp.model.entity.request

data class AllMessageStatisticsRequest(
    val appVersion: String?,
    val messageType: String?,
    val os: String?
)