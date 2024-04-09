package com.example.weathertaskapp.feuture.locations.di

import com.example.weathertaskapp.feuture.locations.UserLocationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureLocationsModule = module {
    viewModel {
        UserLocationsViewModel(get(), get(), get())
    }
}