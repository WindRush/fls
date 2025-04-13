package com.example.fls.bean

import com.example.fls.base.BaseResultBean

data class CurrencyBean(
    val blockchain_symbol: String?,
    val code: String?,
    val coin_id: String?,
    val colorful_image_url: String?,
    val contract_address: String?,
    val deposit_address_tag_name: String?,
    val deposit_address_tag_type: String?,
    val display_decimal: Int?,
    val explorer: String?,
    val gas_limit: Double?,
    val gray_image_url: String?,
    val has_deposit_address_tag: Boolean,
    val is_erc20: Boolean,
    val min_balance: Double?,
    val name: String?,
    val num_confirmation_required: Int?,
    val supports_legacy_address: Boolean,
    val symbol: String?,
    val token_decimal: Int?,
    val token_decimal_value: String?,
    val trading_symbol: String?,
    val withdrawal_eta: List<String?>?
)

class CurrencyResultBean: BaseResultBean() {
    val currencies: List<CurrencyBean>? = null
}