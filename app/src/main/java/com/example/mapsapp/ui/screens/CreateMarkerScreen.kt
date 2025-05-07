package com.example.mapsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.mapsapp.R

@Composable
fun CreateMarkerScreen(navigateToCamera : () -> Unit) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                singleLine = true
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                singleLine = true
            )
            Image(
                painter = painterResource(R.drawable.splash),
                contentDescription = "Marker Icon",
                modifier = Modifier.size(64.dp)
                    .clickable  {navigateToCamera() }, // Navegar a la cámara
            )
            Button(onClick = {
                //Acción para añadir el marcador
            }) {
                Text("Añadir")
            }
        }
    }
}