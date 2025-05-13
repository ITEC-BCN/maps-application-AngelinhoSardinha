package com.example.mapsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.supabasetest.viewmodel.MyViewModel

@Composable
fun MarkerListScreen(navigateToUpdate: (Int) -> Unit = {}) {
    val viewModel = viewModel<MyViewModel>()
    val markers by viewModel.markersList.observeAsState(emptyList())

    // Obtener los marcadores al cargar la pantalla
    LaunchedEffect(Unit) {
        viewModel.getAllMarkers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B2E20)) // Fondo verde oscuro
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de Marcadores",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFA8D5BA), // Verde claro
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp) // Evita corte del primer ítem
        ) {
            items(markers) { marker ->
                viewModel.MarkerItem(marker = marker) {
                    navigateToUpdate(marker.id)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
