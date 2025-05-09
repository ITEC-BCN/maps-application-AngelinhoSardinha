package com.example.supabasetest.viewmodel

    import android.graphics.Bitmap
    import android.os.Build
    import androidx.annotation.RequiresApi
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.border
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import coil.compose.rememberAsyncImagePainter
    import com.example.mapsapp.MyApp
    import com.example.mapsapp.data.model.Marker
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext
    import java.io.ByteArrayOutputStream

    class MyViewModel : ViewModel() {

        val database = MyApp.database

        private val _markersList = MutableLiveData<List<Marker>>()
        val markersList = _markersList

        private val _selectedMarker = MutableLiveData<Marker?>()
        val selectedMarker = _selectedMarker

        val name = MutableLiveData<String>()
        val description = MutableLiveData<String>()
        val latitude = MutableLiveData<String>()
        val longitude = MutableLiveData<String>()

        fun getAllMarkers() {
            CoroutineScope(Dispatchers.IO).launch {
                val dbMarkers = database.getAllMarkers() // Asegúrate de que esta función exista
                withContext(Dispatchers.Main) {
                    _markersList.value = dbMarkers
                }
            }
        }

        fun insertNewMarker(name: String, description: String, coordenadas : String, image: String) {
            val newMarker = Marker(
                id = 0, // Genera un ID único si es necesario
                name = name,
                description = description,
                image = image,
                latlng = coordenadas
            )
            CoroutineScope(Dispatchers.IO).launch {
                database.insertMarker(newMarker) // Asegúrate de que esta función exista
                getAllMarkers()
            }
        }

        fun deleteMarker(id: String) {
            CoroutineScope(Dispatchers.IO).launch {
                database.deleteMarker(id) // Asegúrate de que esta función exista
                getAllMarkers()
            }
        }

        fun editName(value: String) { name.value = value }
        fun editDescription(value: String) { description.value = value }
        fun editLatitude(value: String) { latitude.value = value }
        fun editLongitude(value: String) { longitude.value = value }

        @RequiresApi(Build.VERSION_CODES.O)
        fun insertNewMarkerWithImage(name: String, description: String, latlng : String, imageBitmap: Bitmap?) {
            val stream = ByteArrayOutputStream()
            imageBitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            CoroutineScope(Dispatchers.IO).launch {
                val imageName = database.uploadImage(stream.toByteArray()) // Asegúrate de que esta función exista
                insertNewMarker(name, description, latlng, imageName)
            }
        }

        fun deleteMarkerWithImage(id: String, imageName: String) {
            CoroutineScope(Dispatchers.IO).launch {
                database.deleteImage(imageName) // Asegúrate de que esta función exista
                database.deleteMarker(id)
                getAllMarkers()
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
    }