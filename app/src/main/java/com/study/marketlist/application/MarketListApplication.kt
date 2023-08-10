package com.study.marketlist.application

import android.app.Application
import com.study.marketlist.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarketListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MarketListApplication)
            modules(module)
        }
    }
}