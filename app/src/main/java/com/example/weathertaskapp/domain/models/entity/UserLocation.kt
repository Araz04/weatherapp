package com.example.weathertaskapp.domain.models.entity

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weathertaskapp.domain.utils.WeatherUtil.roundOfToFour

@Entity(tableName = "saved_places")
data class UserLocation(
    @PrimaryKey(autoGenerate = true)
    var id:Long?=null,
    var city:String="",
    var state:String = "",
    var country:String="",
    var lat:Double = 0.0,
    var long:Double = 0.0
)

fun Location.toUserLocation() = UserLocation(lat = this.latitude.roundOfToFour(), long = this.longitude.roundOfToFour())