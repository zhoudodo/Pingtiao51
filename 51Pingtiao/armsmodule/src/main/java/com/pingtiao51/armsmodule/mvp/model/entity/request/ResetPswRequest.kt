package com.pingtiao51.armsmodule.mvp.model.entity.request

data class ResetPswRequest(
        val appVersion: String? = null,
        val os: String? = null,
        val password: String? = null,
        val phone: Long? = null,
        val vcode: String? = null
)