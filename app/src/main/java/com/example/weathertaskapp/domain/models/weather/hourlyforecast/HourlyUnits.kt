package com.example.weathertaskapp.domain.models.weather.hourlyforecast

data class HourlyUnits(
    val apparent_temperature: String,
    val precipitation: String,
    val relativehumidity_2m: String,
    val temperature_180m: String,
    val time: String,
    val weathercode: String,
    val windspeed_180m:String,
    val pressure_msl:String
)