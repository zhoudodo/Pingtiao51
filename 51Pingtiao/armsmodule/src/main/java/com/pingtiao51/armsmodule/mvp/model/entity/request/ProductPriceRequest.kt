package com.pingtiao51.armsmodule.mvp.model.entity.request

import com.blankj.utilcode.util.AppUtils

data class ProductPriceRequest(
        val appVersion: String? = AppUtils.getAppVersionName(),
        val os: String? = "ANDRIOD",
        val value: String? ="PINGTIAO"
)