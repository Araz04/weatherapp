package com.example.weathertaskapp.feuture.locations


import androidx.lifecycle.viewModelScope
import com.example.weathertaskapp.common.base.BaseViewModel
import com.example.weathertaskapp.data.repository.repo.LocalDatabaseRepository
import com.example.weathertaskapp.data.repository.repo.LocationRepository
import com.example.weathertaskapp.data.repository.repo.WeatherRepository
import com.example.weathertaskapp.data.repository.utils.Response
import com.example.weathertaskapp.domain.models.entity.UserLocation
import com.example.weathertaskapp.ui.states.HourlyWeatherState
import com.example.weathertaskapp.ui.states.SavedPlaceState
import com.example.weathertaskapp.ui.states.SearchedLocationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserLocationsViewModel(private val weatherRepository: WeatherRepository,
                             private val locationRepository: LocationRepository,
                             private val localDbRepository: LocalDatabaseRepository
) : BaseViewModel() {

    private val _locationSearchResult = MutableStateFlow(SearchedLocationState())
    val locationSearchResult = _locationSearchResult.asStateFlow()

    private val _currentForecast = MutableStateFlow(HourlyWeatherState())

    private val _savedPlacesState = MutableStateFlow(SavedPlaceState())
    val savedPlaceState = _savedPlacesState.asStateFlow()

    fun getAllSavedPlaces(): Flow<List<UserLocation>> {
        return localDbRepository.getAllPlaces()
    }

    fun getSavedWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getWeatherForSavedPlaces().collectLatest { res ->
                when (res) {
                    is Response.Loading -> _savedPlacesState.update {
                        it.copy(
                            isLoading = true,
                            error = "",
                            isEmpty = false
                        )
                    }
                    is Response.Success -> {
                        res.data?.let { savedWeathers ->
                            //To remove same location from search result.
                            _savedPlacesState.update {
                                it.copy(
                                    isLoading = false,
                                    error = "",
                                    isEmpty = false,
                                    savedPlaces = savedWeathers
                                )
                            }
                        }
                    }
                    is Response.Error -> _savedPlacesState.update {
                        it.copy(
                            isLoading = false,
                            isEmpty = false,
                            error = res.message ?: "Something went wrong."
                        )
                    }
                }
            }
        }
    }

    fun addPlace(place: UserLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            localDbRepository.insertPlace(place)
        }
    }

    fun deletePlace(place: UserLocation) {
        viewModelScope.launch {
            localDbRepository.deletePlace(place)
        }
    }

    fun removePlace(place: UserLocation) {
        deletePlace(place)
        getSavedWeather()
    }

    suspend fun getPlaceByLat(lat: String): UserLocation? {
        return localDbRepository.getLocationByLat(lat)
    }

    fun savePlace(place: UserLocation) {
        addPlace(place)
        getSavedWeather()
    }

    fun getLocation(city: String) {
        viewModelScope.launch {
            locationRepository.getCoordinatesFromName(city).collectLatest { res ->
                when (res) {
                    is Response.Loading -> _locationSearchResult.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Success -> {
                        res.data?.let { userLoc ->
                            //To remove same location from search result.
                            val searchResult = userLoc.distinctBy { it.city + it.state }
                            _locationSearchResult.update {
                                it.copy(
                                    data = searchResult,
                                    isLoading = false,
                                    error = ""
                                )
                            }
                        }
                    }
                    is Response.Error -> _locationSearchResult.update {
                        it.copy(
                            isLoading = false,
                            error = res.message ?: "Something went wrong."
                        )
                    }
                }
            }
        }
    }

    fun removeAllLocationsData(){
        _locationSearchResult.update {
            it.copy(
                data = mutableListOf(),
                isLoading = false,
                error = ""
            )
        }
    }
}