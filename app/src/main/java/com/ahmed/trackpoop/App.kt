package com.ahmed.trackpoop

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.ahmed.trackpoop.di.DataModule
import com.ahmed.trackpoop.di.DomainModule
import com.ahmed.trackpoop.di.PresentationModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    DataModule,
                    PresentationModule,
                    DomainModule
                )
            )
        }
    }
}