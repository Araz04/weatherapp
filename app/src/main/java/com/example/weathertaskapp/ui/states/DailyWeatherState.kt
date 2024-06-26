package com.example.weathertaskapp.ui.states

import com.example.weathertaskapp.domain.mapper.DayWiseForecast

data class DailyWeatherState(
    val forecasts:List<DayWiseForecast> = emptyList(),
    val isLoading:Boolean = true,
    val error:String = ""
)
