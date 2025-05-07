package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mapsapp.ui.navigation.Destination.MarkerCreation
import com.example.mapsapp.ui.screens.CameraScreen
import com.example.mapsapp.ui.screens.CreateMarkerScreen
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.MarkerListScreen

@Composable
fun InternalNavigationWrapper(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, Destination.Map) {
        composable<Destination.Map> {
            MapScreen(
                modifier = modifier,
                navigateToMarker = { latitud, longitud ->
                    navController.navigate(MarkerCreation(latitud, longitud))
                }
            )
        }
        composable<Destination.List> {
            MarkerListScreen()
        }
        composable<MarkerCreation> {
            CreateMarkerScreen(navigateToCamera = {
                navController.navigate(Destination.Camera)
            })
        }
        composable<Destination.Camera> {
            CameraScreen()
        }
    }
}