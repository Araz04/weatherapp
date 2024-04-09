package com.example.weathertaskapp.domain.models.weather.dailyforecast

data class DailyForecastDTO(
    val daily: Daily,
    val daily_units: DailyUnits,
    val elevation: Int,
    val generationtime_ms: Double,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)