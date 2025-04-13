package com.example.fls.bean

import com.example.fls.base.BaseResultBean

data class AssetBean(
    val amount: Double?,
    val currency: String?
)

class AssetResultBean: BaseResultBean() {
    val wallet: List<AssetBean>? = null
}