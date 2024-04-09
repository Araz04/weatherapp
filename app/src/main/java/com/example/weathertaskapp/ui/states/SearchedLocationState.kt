package com.example.weathertaskapp.ui.states

import com.example.weathertaskapp.domain.models.entity.UserLocation

data class SearchedLocationState(
    val data:List<UserLocation> = emptyList(),
    val isLoading:Boolean = true,
    val error:String = ""
)