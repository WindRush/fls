package com.example.fls.bean

data class ConvertAssetData(
    val total: String?,
    val assets: List<ConvertAssetItemData>?
)
data class ConvertAssetItemData(
    val icon: String?,
    val name: String?,
    val symbol: String?,
    val baseValue: String?,
    val convertValue: String?
)