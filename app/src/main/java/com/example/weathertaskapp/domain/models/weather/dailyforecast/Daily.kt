package com.example.weathertaskapp.domain.models.weather.dailyforecast


data class  Daily(
    val precipitation_sum: List<Double>,
    val sunrise: List<String>,
    val sunset: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<String>,
    val weathercode: List<Int>
)

