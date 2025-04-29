package com.example.mapsapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.mapsapp.viewmodel.MarkerViewModel

@Composable
fun MarkerListScreen(markerViewModel: MarkerViewModel, navigateToDetail: (String) -> Unit) {
    // Observar la lista de marcadores desde el ViewModel
    val markers = markerViewModel.markers.observeAsState(emptyList())

    // Configurar la posición inicial de la cámara
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
            LatLng(0.0, 0.0), // Cambia a una posición inicial adecuada
            10f
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // Agregar marcadores al mapa
        markers.value.forEach { marker ->
            Marker(
                title = marker.title,
                snippet = marker.description,
                onClick = {
                    navigateToDetail(marker.id) // Navegar al detalle del marcador
                    true
                }
            )
        }
    }
}