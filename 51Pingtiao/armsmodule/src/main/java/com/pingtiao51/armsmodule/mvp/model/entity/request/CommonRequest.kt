package com.pingtiao51.armsmodule.mvp.model.entity.request

import com.blankj.utilcode.util.AppUtils

data class CommonRequest(
        val appVersion: String? = AppUtils.getAppVersionName(),
        val os: String? = "ANDRIOD"
)