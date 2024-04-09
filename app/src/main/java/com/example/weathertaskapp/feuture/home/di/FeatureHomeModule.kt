package com.example.weathertaskapp.feuture.home.di

import com.example.weathertaskapp.feuture.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {
    viewModel {
        HomeViewModel(get(), get(), get())
    }
}