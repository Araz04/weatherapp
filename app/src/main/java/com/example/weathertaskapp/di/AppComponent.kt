package com.example.weathertaskapp.di

import com.example.weathertaskapp.feuture.home.di.featureHomeModule
import com.example.weathertaskapp.feuture.locations.di.featureLocationsModule
import com.example.weathertaskapp.feuture.navigationdrawer.di.featureNavigationDrawerModule
import com.example.weathertaskapp.feuture.settings.di.featureSettingsModule

val appComponent = listOf(
    appModule,
    locationManagerModule,
    createLocalDBModule,
    createNetworkModule,
    repositoryModule,
    featureNavigationDrawerModule,
    featureHomeModule,
    featureLocationsModule,
    featureSettingsModule
)