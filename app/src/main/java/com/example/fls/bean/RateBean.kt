package com.example.fls.bean

import com.example.fls.base.BaseResultBean

data class RateBean(
    val from_currency: String?,
    val rates: List<Rate>?,
    val time_stamp: Long?,
    val to_currency: String?
)

data class Rate(
    val amount: String?,
    val rate: String?
)

class RateResultBean: BaseResultBean() {
    val tiers: List<RateBean>? = null
}