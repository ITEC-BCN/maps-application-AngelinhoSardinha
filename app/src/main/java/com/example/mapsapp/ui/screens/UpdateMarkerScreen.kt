package com.example.mapsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mapsapp.viewmodels.SupaBaseViewModel
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateMarkerScreen(id: Int, NavigateToBack: () -> Unit) {
    val supaBaseViewModel: SupaBaseViewModel = viewModel()
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val marker by supaBaseViewModel.selectedMarker.observeAsState()

    // Estados locales para los campos de texto
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Inicializa los valores de los campos cuando el marcador cambia
    LaunchedEffect(marker) {
        marker?.let {
            title = it.name
            description = it.description
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            bitmap.value = imageUri.value?.let { uri ->
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
            }
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri.value = it
            bitmap.value = context.contentResolver.openInputStream(it)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Update Marker",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFC8E6C9)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF388E3C)),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Nombre del marcador") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF2E7D32),
                        unfocusedContainerColor = Color(0xFF1B5E20),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFA5D6A7),
                        unfocusedLabelColor = Color(0xFFA5D6A7)
                    )
                )

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción del marcador") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF2E7D32),
                        unfocusedContainerColor = Color(0xFF1B5E20),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFA5D6A7),
                        unfocusedLabelColor = Color(0xFFA5D6A7)
                    )
                )

                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF43A047))
                ) {
                    Text(text = "Seleccionar Imagen", color = Color.White)
                }

                bitmap.value?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(400.dp, 300.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Button(
            onClick = {
                supaBaseViewModel.updateMarker(id, title, description, bitmap.value)
                NavigateToBack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF66BB6A))
        ) {
            Text(text = "Guardar Marcador", color = Color.White)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Selecciona una opción") },
            text = { Text("¿Quieres tomar una foto o elegir una desde la galería?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    val uri = createImageUri(context)
                    imageUri.value = uri
                    launcher.launch(uri!!)
                }) {
                    Text("Tomar Foto", color = Color(0xFF1B5E20))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    pickImageLauncher.launch("image/*")
                }) {
                    Text("Elegir de Galería", color = Color(0xFF1B5E20))
                }
            }
        )
    }
}