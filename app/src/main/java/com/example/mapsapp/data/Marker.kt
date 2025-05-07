package com.example.mapsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Marker(
    val id: Int? = null,
    val name: String,
    val description: String,
    val latitude: Long,
    val longitude: Long,
    val image: String
)
