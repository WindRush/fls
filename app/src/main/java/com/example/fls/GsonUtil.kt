package com.example.fls

import com.google.gson.Gson
import java.io.Reader

private val gson = Gson()

fun <T> parse(json: String?, type: Class<T>?): T? {
    if (json == null || type == null) return null
    val result: T
    try {
        result = gson.fromJson<T>(json, type)
    } catch (ex: Exception) {
        return null
    }
    return result
}