package com.example.weathertaskapp.domain.models.geocoding

data class GeoEncodingDTO(
    val country: String?,
    val lat: Double,
    val local_names: LocalNames?=null,
    val lon: Double,
    val name: String?,
    val state: String?
)