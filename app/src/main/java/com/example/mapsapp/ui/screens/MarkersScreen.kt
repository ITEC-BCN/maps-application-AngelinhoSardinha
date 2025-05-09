package com.example.mapsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.supabasetest.viewmodel.MyViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mapsapp.data.model.Marker

@Composable
fun MarkersScreen(navigateToDetail: (String) -> Unit) {
    val myViewModel = viewModel<MyViewModel>()
    val markersList by myViewModel.markersList.observeAsState(emptyList())
    myViewModel.getAllMarkers()

    val name by myViewModel.name.observeAsState("")
    val description by myViewModel.description.observeAsState("")
    val latlng by myViewModel.latitude.observeAsState("")
    var imageUrl by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Create new marker", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            TextField(value = name, onValueChange = { myViewModel.editName(it) }, label = { Text("Name") })
            TextField(value = description, onValueChange = { myViewModel.editDescription(it) }, label = { Text("Description") })
            TextField(value = latlng, onValueChange = { myViewModel.editLatitude(it) }, label = { Text("Latitude") })
            TextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("Image URL") })
            Button(onClick = {
                myViewModel.insertNewMarker(
                    name,
                    description,
                    latlng,
                    imageUrl
                )
            }) {
                Text("Insert")
            }
        }

        Text(
            "Markers List",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(0.6f)
        ) {
            items(markersList) { marker ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            myViewModel.deleteMarker(marker.id.toString())
                            true
                        } else false
                    }
                )
                SwipeToDismissBox(state = dismissState, backgroundContent = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }) {
                    MarkerItem(marker = marker) { navigateToDetail(marker.id.toString()) }
                }
            }
        }
    }
}

@Composable
fun MarkerItem(marker: Marker, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .border(2.dp, Color.DarkGray)
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(marker.image),
                contentDescription = "Imagen del marcador",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Name: ${marker.name}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Coordinates: ${marker.latlng ?: "N/A"}")
            Text("Description: ${marker.description}")
        }
    }
}
