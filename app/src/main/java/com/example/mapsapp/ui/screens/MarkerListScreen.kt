package com.example.mapsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.supabasetest.viewmodel.MyViewModel

@Composable
fun MarkerListScreen(navigateToUpdate: (Int) -> Unit = {}) {
    val viewModel = viewModel<MyViewModel>()
    val markers by viewModel.markersList.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getAllMarkers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B2E20))
            .padding(top = 130.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = markers, key = { it.id }) { marker ->
                val dismissState = rememberSwipeToDismissBoxState()

                if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart &&
                    dismissState.targetValue == SwipeToDismissBoxValue.EndToStart
                ) {
                    LaunchedEffect(Unit) {
                        viewModel.deleteMarker(marker.id)
                    }
                }

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFD32F2F)),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                text = "Eliminar",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 24.dp)
                            )
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE0F2F1))
                            .clickable { navigateToUpdate(marker.id) }
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(marker.image) // Asegúrate que marker.image existe y es válido
                                .crossfade(true)
                                .build(),
                            contentDescription = "Imagen del marcador",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "📍 ${marker.name}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF004D40)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = marker.description,
                            fontSize = 14.sp,
                            color = Color(0xFF37474F)
                        )
                        Text(
                            text = "Coordenadas: " + marker.latlng,
                            fontSize = 14.sp,
                            color = Color(0xFF37474F)
                        )
                    }
                }
            }
        }
    }
}
