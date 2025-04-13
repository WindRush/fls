package com.example.fls

import android.app.Application
import android.content.Context

class FlsApp: Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}