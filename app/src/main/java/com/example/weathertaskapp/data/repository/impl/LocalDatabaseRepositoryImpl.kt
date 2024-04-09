package com.example.weathertaskapp.data.repository.impl

import com.example.weathertaskapp.data.local.WeatherDao
import com.example.weathertaskapp.data.repository.repo.LocalDatabaseRepository
import com.example.weathertaskapp.domain.models.entity.UserLocation
import kotlinx.coroutines.flow.Flow

class LocalDatabaseRepositoryImpl(
    private val dao: WeatherDao
): LocalDatabaseRepository {

    override fun getAllPlaces(): Flow<List<UserLocation>> {
       return dao.getAllPlaces()
    }

    override suspend fun insertPlace(place: UserLocation) {
        dao.insertPlace(place)
    }

    override suspend fun deletePlace(place: UserLocation) {
        dao.deletePlace(place)
    }

    override suspend fun getLocationByLat(lat:String):UserLocation?{
        return dao.getLocationByLat(lat)
    }
}