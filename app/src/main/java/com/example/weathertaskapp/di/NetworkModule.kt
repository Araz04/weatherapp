package com.example.weathertaskapp.di

import com.example.weathertaskapp.data.remote.GeoEncodingApiService
import com.example.weathertaskapp.data.remote.WeatherApiService
import com.example.weathertaskapp.domain.utils.GeoEncodingUtil
import com.example.weathertaskapp.domain.utils.WeatherUtil
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val createNetworkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single { provideOkHttpClient(get()) }
    single { provideGeoEncodingApi(get()) }
    single { provideWeatherApi(get()) }
}

private fun provideGeoEncodingApi(okHttpClient: OkHttpClient): GeoEncodingApiService {
    return Retrofit.Builder()
        .baseUrl(GeoEncodingUtil.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
        .create(GeoEncodingApiService::class.java)
}

private fun provideWeatherApi(okHttpClient: OkHttpClient): WeatherApiService {
    return Retrofit.Builder()
        .baseUrl(WeatherUtil.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
        .create(WeatherApiService::class.java)
}

private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(loggingInterceptor)
        .build()
}