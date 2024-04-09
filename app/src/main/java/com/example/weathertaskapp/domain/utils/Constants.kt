package com.example.weathertaskapp.domain.utils

import kotlin.math.roundToInt

object GeoEncodingUtil {
    const val API_KEY = "aa1a555cf165cbbd0938f58b70f92c24" //Replace your api key here
    const val baseUrl = "https://api.openweathermap.org"
}

object WeatherUtil{
    const val baseUrl = "https://api.open-meteo.com"

    fun Double.roundOfToFour():Double{
        return (this * 10000.0).roundToInt() /10000.0
    }
}