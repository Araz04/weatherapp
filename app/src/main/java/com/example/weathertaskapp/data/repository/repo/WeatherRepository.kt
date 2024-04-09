package com.example.weathertaskapp.data.repository.repo

import com.example.weathertaskapp.data.repository.utils.Response
import com.example.weathertaskapp.domain.models.entity.SavedLocationWeatherOverview
import com.example.weathertaskapp.domain.models.weather.dailyforecast.DailyForecastDTO
import com.example.weathertaskapp.domain.models.weather.hourlyforecast.HourlyForecastDTO
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getHourlyForecast(lat: String, long: String): Flow<Response<HourlyForecastDTO>>
    suspend fun getDailyForecast(lat: String, long: String): Flow<Response<DailyForecastDTO>>
    suspend fun getWeatherForSavedPlaces(): Flow<Response<List<SavedLocationWeatherOverview>>>
}