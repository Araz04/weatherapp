package com.example.weathertaskapp.ui.states

import com.example.weathertaskapp.domain.models.entity.UserLocation

data class LocationState(
    val location: UserLocation = UserLocation(),
    val isLoading:Boolean = true,
    val error:String = ""
)
