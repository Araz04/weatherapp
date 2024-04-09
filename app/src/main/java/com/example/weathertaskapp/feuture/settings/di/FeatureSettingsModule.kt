package com.example.weathertaskapp.feuture.settings.di

import com.example.weathertaskapp.feuture.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureSettingsModule = module {
    viewModel {
        SettingsViewModel()
    }
}