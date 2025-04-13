package com.example.fls.main

import com.example.fls.Utils
import com.example.fls.bean.AssetResultBean
import com.example.fls.bean.CurrencyBean
import com.example.fls.bean.CurrencyResultBean
import com.example.fls.bean.RateBean
import com.example.fls.bean.RateResultBean
import com.example.fls.parse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepository {
    suspend fun requestBalancesFlow(): Flow<AssetResultBean?> {
        return flow {
            delay(150) // 模拟网络延时
            val balances = parse(Utils.readAssetFile("wallet-balance.json"), AssetResultBean::class.java)
            emit(if (!Utils.available(balances)) null else balances)
        }.flowOn(Dispatchers.IO)

    }
    suspend fun requestRatesFlow(): Flow<Map<String?, RateBean>?> {
        return flow {
            delay(200) // 模拟网络延时
            val rates = parse(Utils.readAssetFile("live-rates.json"), RateResultBean::class.java)
            val rateMap = if (!Utils.available(rates)) null else rates?.tiers?.associate { it.from_currency to it }
            emit(rateMap)
        }.flowOn(Dispatchers.IO)

    }
    suspend fun requestCurrenciesFlow(): Flow<Map<String?, CurrencyBean>?> {
        return flow {
            delay(100) // 模拟网络延时
            val currencies = parse(Utils.readAssetFile("currencies.json"), CurrencyResultBean::class.java)
            val currencyMap = if (!Utils.available(currencies)) null else currencies?.currencies?.associate { it.symbol to it }
            emit(currencyMap)
        }.flowOn(Dispatchers.IO)
    }
}