package com.example.weathertaskapp.domain.location

import android.location.Location
import com.example.weathertaskapp.data.repository.utils.Response
import kotlinx.coroutines.flow.Flow

interface LocationClient {

     fun getLocation(): Flow<Response<Location>>

    class LocationException( message:String):Exception(message)
}
