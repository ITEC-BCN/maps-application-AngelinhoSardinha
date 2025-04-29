package com.example.mapsapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.MarkerListScreen
import com.example.mapsapp.viewmodel.MarkerViewModel

@Composable
fun InternalNavigationWrapper(navController: NavHostController, padding: Modifier) {
    NavHost(navController, Destination.Map){
        composable<Destination.Map>{
            MapScreen(
                modifier = padding,
                navigateToMarker = {
                    navController.navigate(Destination.MarkerCreation){
                        popUpTo(Destination.Map){
                            saveState = true
                        }
                    }
                }
            )
        }
        composable<Destination.List> {
            MarkerListScreen()
        }
    }
}