package com.example.weathertaskapp.domain.models.entity

import com.example.weathertaskapp.domain.mapper.HourlyForecast

data class SavedLocationWeatherOverview(
    val location: UserLocation,
    val forecast : HourlyForecast
)
