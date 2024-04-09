package com.example.weathertaskapp.ui.states

import com.example.weathertaskapp.domain.mapper.HourlyForecast


data class HourlyWeatherState(
    val forecasts:Map<Int,List<HourlyForecast>> = emptyMap(),
    val isLoading:Boolean = true,
    val error:String = ""
)
