package com.example.weathertaskapp.data.repository.repo

import com.example.weathertaskapp.domain.models.entity.UserLocation
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {
    fun getAllPlaces(): Flow<List<UserLocation>>

    suspend fun insertPlace(place:UserLocation)

    suspend fun getLocationByLat(lat:String):UserLocation?

    suspend fun deletePlace(place: UserLocation)
}