package com.example.weathertaskapp.di

import android.content.Context
import androidx.room.Room
import com.example.weathertaskapp.data.local.WeatherDatabase
import com.example.weathertaskapp.data.repository.impl.LocalDatabaseRepositoryImpl
import com.example.weathertaskapp.data.repository.repo.LocalDatabaseRepository
import org.koin.dsl.module

val createLocalDBModule = module {
    single { provideRoomDatabase(get()) }
    single<LocalDatabaseRepository> { LocalDatabaseRepositoryImpl(get<WeatherDatabase>().dao) } // Define your LocalDatabaseRepository binding
}

fun provideRoomDatabase(context: Context): WeatherDatabase =
    Room.databaseBuilder(
        context = context,
        klass = WeatherDatabase::class.java,
        name = WeatherDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()