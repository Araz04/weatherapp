package com.example.weathertaskapp.domain.mapper

data class IndexedWeatherInfo(
    val index:Int,
    val hourlyForecast: HourlyForecast
)
