package com.example.weathertaskapp.data.repository.repo

import com.example.weathertaskapp.data.repository.utils.Response
import com.example.weathertaskapp.domain.models.entity.UserLocation
import com.example.weathertaskapp.domain.models.geocoding.GeoEncodingDTO
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getNameFromCoordinates(
        latitude: String,
        longitude: String
    ): Flow<Response<GeoEncodingDTO>>

    suspend fun getCoordinatesFromName(name: String): Flow<Response<List<UserLocation>>>
}