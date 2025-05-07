package com.example.supabasetest.viewmodel

    import android.graphics.Bitmap
    import android.os.Build
    import androidx.annotation.RequiresApi
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
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

        fun insertNewMarker(name: String, description: String, latitude: Double, longitude: Double, image: String) {
            val newMarker = Marker(
                id = "", // Genera un ID único si es necesario
                name = name,
                description = description,
                latitude = latitude,
                longitude = longitude,
                image = image
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
        fun insertNewMarkerWithImage(name: String, description: String, latitude: Double, longitude: Double, imageBitmap: Bitmap?) {
            val stream = ByteArrayOutputStream()
            imageBitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            CoroutineScope(Dispatchers.IO).launch {
                val imageName = database.uploadImage(stream.toByteArray()) // Asegúrate de que esta función exista
                insertNewMarker(name, description, latitude, longitude, imageName)
            }
        }

        fun deleteMarkerWithImage(id: String, imageName: String) {
            CoroutineScope(Dispatchers.IO).launch {
                database.deleteImage(imageName) // Asegúrate de que esta función exista
                database.deleteMarker(id)
                getAllMarkers()
            }
        }
    }