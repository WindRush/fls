package com.example.fls.main.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fls.Utils
import com.example.fls.base.BaseViewModel
import com.example.fls.bean.ConvertAssetData
import com.example.fls.bean.ConvertAssetItemData
import com.example.fls.main.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MainViewModel : BaseViewModel() {
    // 监听状态改变
    private val _uiStateFlow = MutableStateFlow<String?>(null)
    val uiStateFlow: StateFlow<String?> = _uiStateFlow.asStateFlow()

    // 数据改变
    private val _balancesLiveData = MutableLiveData<ConvertAssetData?>()
    val balancesLiveData: LiveData<ConvertAssetData?> = _balancesLiveData

    private val mainRes = MainRepository()

    fun requestCombineData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val balanceFlow = mainRes.requestBalancesFlow()
                val ratesFlow = mainRes.requestRatesFlow()
                val currenciesFlow = mainRes.requestCurrenciesFlow()

                // 组装数据，避免RecyclerView渲染时卡顿
                combine(balanceFlow, ratesFlow, currenciesFlow) { balances, rates, currencies ->
                    if (balances == null || rates == null || currencies == null) {
                        null
                    } else {
                        var total = "0"
                        val comItemData = balances.wallet?.map {
                            val currency = currencies[it.currency]
                            val rates = rates[it.currency]?.rates
                            val convertValue = Utils.rateCalValue(
                                it.amount?.toString(),
                                rates?.firstOrNull()?.rate
                            )
                            total = addTotal(total, convertValue)
                            ConvertAssetItemData(
                                icon = currency?.colorful_image_url,
                                name = currency?.name,
                                symbol = currency?.symbol,
                                baseValue = it.amount?.toString(),
                                convertValue = convertValue
                            )
                        }
                        ConvertAssetData(total, comItemData)
                    }
                }.catch {
                    _errorLiveData.postValue("数据错误$it")
                }.collect {
                    // 更新
                    _uiStateFlow.value = (it?.total)
                    _balancesLiveData.postValue(it)
                }
            } catch (ex: Exception) {
                _errorLiveData.postValue("数据错误:$ex")
            }
        }

    }


    private fun addTotal(total: String, value: String?): String {
        return try {
            BigDecimal(total).add(BigDecimal(value)).toPlainString()
        } catch (ex: Exception) {
            total
        }
    }

}