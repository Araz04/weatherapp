package com.example.weathertaskapp.presentation

import android.app.Application
import com.example.weathertaskapp.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherTaskApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@WeatherTaskApplication)
            modules(
                appComponent
            )
        }
    }
}