package com.example.weathertaskapp.feuture.navigationdrawer.di

import com.example.weathertaskapp.feuture.navigationdrawer.NavigationDrawerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureNavigationDrawerModule = module {
    viewModel { NavigationDrawerViewModel() }
}