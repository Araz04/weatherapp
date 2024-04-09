package com.example.weathertaskapp.di

import com.example.weathertaskapp.domain.location.DefaultLocationManager
import com.example.weathertaskapp.domain.location.LocationClient
import com.example.weathertaskapp.ui.preferences.PreferencesWeatherApp
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single { PreferencesWeatherApp.getInstance(androidContext()) }
    single { PreferencesWeatherApp.getInstance(androidContext()) }
}

val locationManagerModule = module {
    single<LocationClient> {
        DefaultLocationManager(
            androidContext(),
            LocationServices.getFusedLocationProviderClient(androidContext())
        )
    }
}