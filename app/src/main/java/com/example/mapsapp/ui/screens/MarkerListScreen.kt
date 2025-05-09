package com.example.mapsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.data.model.Marker
import com.example.supabasetest.viewmodel.MyViewModel

@Composable
fun MarkerListScreen(onMarkerClick: (Marker) -> Unit = {}) {
    val viewModel = viewModel<MyViewModel>()
    val markers by viewModel.markersList.observeAsState(emptyList())

    // Obtener los marcadores al cargar la pantalla
    LaunchedEffect(Unit) {
        viewModel.getAllMarkers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Lista de Marcadores",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp) // Agrega un padding superior para evitar que el primer ítem se corte
        ) {
            items(markers) { marker ->
                MarkerItem(marker = marker) {
                    onMarkerClick(marker)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}