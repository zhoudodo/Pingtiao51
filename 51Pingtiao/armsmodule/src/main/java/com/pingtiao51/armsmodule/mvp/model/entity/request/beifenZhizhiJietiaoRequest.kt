package com.pingtiao51.armsmodule.mvp.model.entity.request

data class beifenZhizhiJietiaoRequest(
    val amount: String?,
    val appVersion: String?,
    val borrower: String?,
    val comment: String?,
    val lender: String?,
    val loanUsage: String?,
    val os: String?,
    val repaymentDate: String?,
    val urls: List<String?>?
)
//{
//    "amount": 1100,
//    "appVersion": "1.2.0",
//    "borrower": "张三",
//    "comment": "啊啊啊啊",
//    "lender": "李四",
//    "loanUsage": "购物",
//    "os": "IOS",
//    "page": 1,
//    "repaymentDate": "2019-10-02",
//    "size": 20,
//    "urls": [
//    "1",
//    "2"
//    ]
//}