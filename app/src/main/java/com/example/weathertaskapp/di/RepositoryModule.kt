package com.example.weathertaskapp.di

import com.example.weathertaskapp.data.local.WeatherDatabase
import com.example.weathertaskapp.data.repository.impl.LocationRepositoryImpl
import com.example.weathertaskapp.data.repository.impl.WeatherRepositoryImpl
import com.example.weathertaskapp.data.repository.repo.LocationRepository
import com.example.weathertaskapp.data.repository.repo.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<LocationRepository> { LocationRepositoryImpl(get(), get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get<WeatherDatabase>().dao, get()) }
}