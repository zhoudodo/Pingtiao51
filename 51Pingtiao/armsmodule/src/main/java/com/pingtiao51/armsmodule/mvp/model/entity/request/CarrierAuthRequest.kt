package com.pingtiao51.armsmodule.mvp.model.entity.request

import com.blankj.utilcode.util.AppUtils

data class CarrierAuthRequest(
        val appVersion: String? = AppUtils.getAppVersionName(),
        val bankcardNo: String? = null,
        val identityNo: String?,
        val name: String?,
        val os: String? = "ANDRIOD",
        val phone: String?
//        val vcode: String?
)