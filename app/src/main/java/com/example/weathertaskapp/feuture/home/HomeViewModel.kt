package com.example.weathertaskapp.feuture.home


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weathertaskapp.ui.states.DailyWeatherState
import com.example.weathertaskapp.ui.states.HourlyWeatherState
import com.example.weathertaskapp.common.base.BaseViewModel
import com.example.weathertaskapp.data.repository.repo.LocationRepository
import com.example.weathertaskapp.data.repository.repo.WeatherRepository
import com.example.weathertaskapp.data.repository.utils.Response
import com.example.weathertaskapp.domain.location.LocationClient
import com.example.weathertaskapp.domain.mapper.toDailyForecasts
import com.example.weathertaskapp.domain.mapper.toDailyHourlyForecast
import com.example.weathertaskapp.domain.models.entity.toUserLocation
import com.example.weathertaskapp.ui.states.LocationState
import com.example.weathertaskapp.ui.states.UserData
import com.example.weathertaskapp.ui.states.UserDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val weatherRepo: WeatherRepository,
                    private val defaultLocationManager: LocationClient,
                    private val locationRepo: LocationRepository
) : BaseViewModel() {

    val currentLocation = MutableStateFlow(LocationState())

    val dailyHourlyForecast = MutableStateFlow(HourlyWeatherState())

    val dailyForecast = MutableStateFlow(DailyWeatherState())

    private val _userData = MutableStateFlow(UserDataState())
    val userData = _userData.asStateFlow()

    fun loadData() {
        getCurrentLocation()
        getUserData()
    }

    private fun getUserData() {
        _userData.update { it.copy(isLoading = true) }
        combine(
            currentLocation,
            dailyHourlyForecast,
            dailyForecast
        ) { location, hourlyForecast, dailyForecast ->
            when {
                location.isLoading || hourlyForecast.isLoading || dailyForecast.isLoading -> _userData.update {
                    it.copy(
                        isLoading = true,
                        error = ""
                    )
                }
                location.error.isNotEmpty() || hourlyForecast.error.isNotEmpty() || dailyForecast.error.isNotEmpty() -> _userData.update {
                    it.copy(
                        isLoading = false,
                        error = if (location.error.isNotEmpty()) location.error else if (hourlyForecast.error.isNotEmpty()) hourlyForecast.error else dailyForecast.error
                    )
                }

                location.location.city.isNotEmpty() && hourlyForecast.forecasts.isNotEmpty() && dailyForecast.forecasts.isNotEmpty() -> _userData.update {

                    it.copy(
                        userData = UserData(
                            hourlyForecast = hourlyForecast.forecasts,
                            location = location.location,
                            dailyForecast = dailyForecast.forecasts
                        ),
                        isLoading = false,
                        error = ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            defaultLocationManager.getLocation().collectLatest { res ->
                when (res) {
                    is Response.Loading ->
                        currentLocation.update {
                            it.copy(
                                isLoading = true,
                                error = ""
                            )
                        }
                    is Response.Success -> {
                        res.data?.let { loc ->
                            val userLoc = loc.toUserLocation()
                            currentLocation.update {
                                it.copy(
                                    location = userLoc,
                                    isLoading = false,
                                    error = ""
                                )
                            }

                            val coordinate = async {
                                getNameFromCoordinates(
                                    lat = userLoc.lat.toString(),
                                    long = userLoc.long.toString()
                                )
                            }
                            val hourlyForecast = async {
                                getDailyHourlyForecast(
                                    lat = userLoc.lat.toString(),
                                    long = userLoc.long.toString()
                                )
                            }
                            val dailyForecast = async {
                                getDailyForecast(
                                    lat = userLoc.lat.toString(),
                                    long = userLoc.long.toString()
                                )
                            }

                            coordinate.await()
                            hourlyForecast.await()
                            dailyForecast.await()

                        }
                    }
                    is Response.Error -> currentLocation.update {
                        it.copy(
                            isLoading = false,
                            error = res.message ?: "Something went wrong."
                        )
                    }
                }
            }
        }
    }

    private fun getNameFromCoordinates(lat: String, long: String) {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepo.getNameFromCoordinates(lat, long).collectLatest { res ->
                when (res) {
                    is Response.Loading -> currentLocation.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Success -> {
                        res.data?.let { geo ->
                            currentLocation.update {
                                it.copy(
                                    location = it.location.copy(
                                        city = geo.name ?: "",
                                        state = geo.state ?: "",
                                        country = geo.country ?: ""
                                    ),
                                    isLoading = false,
                                    error = ""
                                )
                            }
                        }
                    }
                    is Response.Error -> currentLocation.update {
                        it.copy(
                            isLoading = false,
                            error = res.message ?: "Something went wrong."
                        )
                    }
                }
            }
        }
    }

    private fun getDailyHourlyForecast(lat: String, long: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepo.getHourlyForecast(lat, long).collectLatest { res ->
                when (res) {
                    is Response.Loading -> dailyHourlyForecast.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Error -> dailyHourlyForecast.update {
                        Log.e("ViewModel", res.message.toString())
                        it.copy(
                            isLoading = false,
                            error = res.message ?: "An unknown error occurred"
                        )
                    }
                    is Response.Success -> {
                        if (res.data == null) {
                            dailyHourlyForecast.update {
                                it.copy(
                                    isLoading = false,
                                    error = res.message ?: "Sorry can't fetch weather right now."
                                )
                            }
                        } else {
                            val data = res.data.toDailyHourlyForecast()
                            dailyHourlyForecast.update {
                                it.copy(
                                    forecasts = data,
                                    isLoading = false,
                                    error = ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getDailyForecast(lat: String, long: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepo.getDailyForecast(lat, long).collectLatest { res ->
                when (res) {
                    is Response.Loading -> dailyForecast.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Error -> dailyForecast.update {
                        Log.e("ViewModel dailyForecast", res.message.toString())
                        it.copy(
                            isLoading = false,
                            error = res.message ?: "An unknown error occurred"
                        )
                    }
                    is Response.Success -> {
                        if (res.data == null) {
                            dailyForecast.update {
                                it.copy(
                                    isLoading = false,
                                    error = res.message ?: "Sorry can't fetch weather right now."
                                )
                            }
                        } else {
                            val data = res.data.toDailyForecasts()
                            dailyForecast.update {
                                it.copy(
                                    forecasts = data,
                                    isLoading = false,
                                    error = ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}