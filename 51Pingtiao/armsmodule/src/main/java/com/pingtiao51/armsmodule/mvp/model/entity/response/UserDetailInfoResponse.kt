package com.pingtiao51.armsmodule.mvp.model.entity.response

data class UserDetailInfoResponse(
        val identityNo: String?,
        val isSignPasswordSet: Boolean?,
        val isVerified: Boolean?,
        val phone: String?,
        val realname: String?,
        val waitToCollect: String?,
        val waitToRepaid: String?
)