package com.example.mapsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.mapsapp.viewmodels.SupaBaseViewModel
import com.example.supabasetest.viewmodel.MyViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(modifier: Modifier = Modifier, navigateToMarker: (String) -> Unit) {
    val viewModel : SupaBaseViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val markers = viewModel.markerList.observeAsState()
    Column(modifier.fillMaxSize()) {
        val itb = LatLng(41.4534225, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 17f)
        }
        GoogleMap(
            Modifier.fillMaxSize(), cameraPositionState = cameraPositionState,
            onMapClick = {
                Log.d("MAP CLICKED", it.toString())
            }, onMapLongClick = {
                navigateToMarker(it.latitude.toString() + "," + it.longitude.toString())
                Log.d("MAP CLICKED LONG", it.toString())
            })
        {
            markers.value?.forEach { marker ->
                Log.d("DEBUG_MARKER", "Marcador: ${marker.name} - ${marker.latlng}")
                val latLngParts = marker.latlng.split(",")
                if (latLngParts.size == 2) {
                    val lat = latLngParts[0].trim().toDoubleOrNull()
                    val lon = latLngParts[1].trim().toDoubleOrNull()
                    if (lat != null && lon != null) {
                        val position = LatLng(lat, lon)
                        Marker(
                            state = MarkerState(position = position),
                            title = marker.name,
                            snippet = marker.description
                        )
                    }
                }
            }
        }
    }
}