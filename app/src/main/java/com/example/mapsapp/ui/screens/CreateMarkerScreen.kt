package com.example.mapsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateMarkerScreen(coordenadas: String, modifier: Modifier, navigateToMarker: () -> Unit) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = "Crear Marcador", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Coordenadas: $coordenadas")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = navigateToMarker) {
            Text(text = "Guardar Marcador")
        }
    }
}