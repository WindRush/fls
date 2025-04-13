package com.example.fls

import com.example.fls.base.BaseResultBean
import com.google.gson.Gson
import java.math.BigDecimal

object Utils {
    fun available(baseResultBean: BaseResultBean?): Boolean {
        return baseResultBean?.ok == true
    }

    /**
     * 汇率换算工具
     */
    fun rateCalValue(value: String?, rate: String?): String {
        return try {
            BigDecimal(value).multiply(BigDecimal(rate)).toPlainString()
        } catch (ex: Exception) {
            "0"
        }

    }

    /**
     * 读取assets文件
     */
    fun readAssetFile(fileName: String): String? {
        return try {
            FlsApp.appContext.assets.open(fileName).use { inputStream ->
                inputStream.bufferedReader().use { reader ->
                    reader.readText()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}