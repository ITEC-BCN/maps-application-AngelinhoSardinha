package com.example.mapsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.data.model.MarkerData

class MarkerViewModel : ViewModel() {
    private val _markers = MutableLiveData<List<MarkerData>>()
    val markers: LiveData<List<MarkerData>> get() = _markers

    init {
        // Cargar marcadores iniciales
        _markers.value = listOf(
            MarkerData("1", "Marker 1", "Descripción 1", 37.7749, -122.4194),
            MarkerData("2", "Marker 2", "Descripción 2", 34.0522, -118.2437)
        )
    }
}