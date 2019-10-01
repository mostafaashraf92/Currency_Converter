package com.revolut.currency

import android.app.Application
import com.revolut.currency.di.appModule
import com.revolut.currency.di.networkModule
import com.revolut.currency.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApplication)
            androidFileProperties()
            modules(
                listOf(
                    appModule, networkModule, viewModelModule
                )
            )
        }
    }
}