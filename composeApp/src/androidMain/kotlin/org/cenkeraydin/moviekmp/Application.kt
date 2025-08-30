package org.cenkeraydin.moviekmp

import android.app.Application
import android.content.Context
import org.cenkeraydin.moviekmp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

lateinit var appContext: Context


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule())
        }
    }
}