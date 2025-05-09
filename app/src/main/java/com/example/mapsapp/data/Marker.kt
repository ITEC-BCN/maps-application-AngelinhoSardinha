package com.example.mapsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Marker(
    val id: Int = 0,
    val name: String,
    val description: String,
    val latlng: String, // Cambiado de latitude y longitude a un solo campo
    val image: String
)
