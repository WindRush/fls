package com.example.fls.bean

data class RateBean(
    val from_currency: String,
    val rates: List<Rate>,
    val time_stamp: Int,
    val to_currency: String
)

data class Rate(
    val amount: String,
    val rate: String
)