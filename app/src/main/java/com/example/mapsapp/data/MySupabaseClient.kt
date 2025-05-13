package com.example.mapsapp.data


import android.R.attr.id
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mapsapp.BuildConfig
import com.example.mapsapp.data.model.Marker
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MySupabaseClient {
    lateinit var client: SupabaseClient
    lateinit var storage: Storage
    private val supabaseUrl = BuildConfig.SUPABASE_URL

    private val supabaseKey = BuildConfig.SUPABASE_KEY
    constructor() {
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
            install(Storage)
        }
        storage = client.storage
    }
    //SQL operations
    suspend fun getAllMarkers(): List<Marker> {
        return client.from("Marker").select().decodeList<Marker>()
    }

    suspend fun getMarkers(id: String): Marker {
        return client.from("Marker").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<Marker>()
    }

    suspend fun insertMarker(marker: Marker) {
        client.from("Marker").insert(marker)
    }

    suspend fun deleteMarker(id: Int){
        client.from("Marker").delete{ filter { eq("id", id) } }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage(imageFile: ByteArray): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val imageName = storage.from("images").upload(path = "image_${fechaHoraActual.format(formato)}.png", data = imageFile)
        return buildImageUrl(imageFileName = imageName.path)
    }

    fun buildImageUrl(imageFileName: String) = "${this.supabaseUrl}/storage/v1/object/public/images/${imageFileName}"

    suspend fun updateMarker(id : Int, title: String, description: String, imageName: String, imageFile: ByteArray) {
        val updatedImageName = storage.from("images").update(path = imageName, data = imageFile)
        client.from("Marker").update({
            set("name", title)
            set("description", description)
            set("image", buildImageUrl(imageFileName = updatedImageName.path))
        }) {
            filter {
                eq("id", id)
            }
        }
    }

    suspend fun deleteImage(imageName: String){
        val imgName = imageName.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        client.storage.from("images").delete(imgName)
    }
}