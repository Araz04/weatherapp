package com.example.weathertaskapp.ui.states

import com.example.weathertaskapp.domain.mapper.DayWiseForecast
import com.example.weathertaskapp.domain.mapper.HourlyForecast
import com.example.weathertaskapp.domain.models.entity.UserLocation

data class UserDataState(
    val userData: UserData?=null,
    val isLoading:Boolean = true,
    val error:String = ""
)

data class UserData(
    val hourlyForecast:Map<Int,List<HourlyForecast>>,
    val dailyForecast:List<DayWiseForecast>,
    val location: UserLocation
)