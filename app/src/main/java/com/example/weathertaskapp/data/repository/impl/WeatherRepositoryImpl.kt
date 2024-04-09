package com.example.weathertaskapp.data.repository.impl

import com.example.weathertaskapp.data.local.WeatherDao
import com.example.weathertaskapp.data.remote.WeatherApiService
import com.example.weathertaskapp.data.repository.repo.WeatherRepository
import com.example.weathertaskapp.data.repository.utils.Response
import com.example.weathertaskapp.domain.mapper.toDailyHourlyForecast
import com.example.weathertaskapp.domain.models.entity.SavedLocationWeatherOverview
import com.example.weathertaskapp.domain.models.weather.dailyforecast.DailyForecastDTO
import com.example.weathertaskapp.domain.models.weather.hourlyforecast.HourlyForecastDTO
import com.example.weathertaskapp.ui.preferences.PreferencesWeatherApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalTime

class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService,
    private val dao: WeatherDao,
    private val preferencesHelper: PreferencesWeatherApp
) :
    WeatherRepository {

    override suspend fun getHourlyForecast(
        lat: String,
        long: String
    ): Flow<Response<HourlyForecastDTO>> = flow {
        emit(Response.Loading())
        try {
            val tempUnit = if (preferencesHelper.getTemp() == 1) "fahrenheit" else null
            val windUnit = if (preferencesHelper.getWind() == 1) "ms" else null
            val response = weatherApiService.getHourlyForecast(lat, long, tempUnit, windUnit)
            emit(Response.Success(response))

        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "Couldn't reach server check your internet connection"))
        }
    }

    override suspend fun getDailyForecast(
        lat: String,
        long: String
    ): Flow<Response<DailyForecastDTO>> = flow {
        emit(Response.Loading())
        try {
            val response = weatherApiService.getDailyForecast(lat, long)
            emit(Response.Success(response))

        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "Couldn't reach server check your internet connection"))
        }
    }

    override suspend fun getWeatherForSavedPlaces(): Flow<Response<List<SavedLocationWeatherOverview>>> =
        flow {
            try {
                emit(Response.Loading())
                val hourIndex = LocalTime.now().hour
                val locations = dao.getAllPlaces().first()

                if (locations.isEmpty()) {
                    emit(Response.Success(emptyList()))
                } else {
                    val savedWeather = locations.map { location ->
                        val weather = weatherApiService.getHourlyForecast(
                            location.lat.toString(),
                            location.long.toString()
                        ).toDailyHourlyForecast()[0]?.get(hourIndex)!!
                        SavedLocationWeatherOverview(
                            location,
                            weather
                        )
                    }
                    emit(Response.Success(savedWeather))
                }

            } catch (e: HttpException) {
                emit(Response.Error(message = "Oops, something went wrong"))
            } catch (e: IOException) {
                emit(Response.Error(message = "Couldn't reach server check your internet connection"))
            } catch (e: Exception) {
                emit(Response.Error(message = "Oops, something went wrong"))
            }
        }
}