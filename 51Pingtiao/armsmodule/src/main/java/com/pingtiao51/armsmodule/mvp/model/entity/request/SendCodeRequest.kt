package com.pingtiao51.armsmodule.mvp.model.entity.request

data class SendCodeRequest(
        val appVersion: String? = null,
        val os: String? = null,
        val phone: String? = null,
        val vcodeType: String? = null
)

/**
 * appVersion : 1.2.0
 * os : IOS
 * phone : 13616534527
 * vcodeType : REGISTER
 */



