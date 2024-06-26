package com.example.weathertaskapp.ui.states

import com.example.weathertaskapp.domain.models.entity.SavedLocationWeatherOverview

data class SavedPlaceState(
    val savedPlaces: List<SavedLocationWeatherOverview> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = "",
    val isEmpty: Boolean = false
)
