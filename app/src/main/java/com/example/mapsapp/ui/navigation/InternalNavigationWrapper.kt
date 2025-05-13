package com.example.mapsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mapsapp.ui.navigation.Destination.MarkerCreation
import com.example.mapsapp.ui.screens.CameraScreen
import com.example.mapsapp.ui.screens.CreateMarkerScreen
import com.example.mapsapp.ui.screens.DetailMarkerScreen
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.MarkerListScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InternalNavigationWrapper(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, Destination.Map) {
        composable<Destination.Map> {
            MapScreen(
                modifier = modifier,
                navigateToMarker = { latlng ->
                    navController.navigate(MarkerCreation(latlng))
                }
            )
        }
        composable<Destination.List> {
            MarkerListScreen(
                navigateToDetail = { id ->
                    navController.navigate(Destination.MarkerDetail(id))
                }
            )
        }
        composable<MarkerCreation> { backStackEntry ->
            val latlng = backStackEntry.arguments?.getString("latlng") ?: ""
            CreateMarkerScreen(
                cordenadas = latlng,
                NavigateToBack = {
                    navController.navigate(Destination.Map) {
                        popUpTo(Destination.Map) { inclusive = true }
                    }
                }
            )
        }
        composable<Destination.Camera> {
            CameraScreen()
        }
        composable<Destination.MarkerDetail> {
            val id = it.arguments?.getInt("id") ?: 0
            DetailMarkerScreen(id,
                NavigateToBack = {
                    navController.navigate(Destination.List) {
                        popUpTo(Destination.List) { inclusive = true }
                    }
                }
            )
        }
    }
}