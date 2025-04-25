package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.navigation.Destination.Drawer
import com.example.mapsapp.ui.navigation.Destination.Permissions
import com.example.mapsapp.ui.screens.DrawerScreen
import com.example.mapsapp.ui.screens.PermissionScreen

@Composable
fun MainNavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController, Permissions) {
        composable<Permissions> {
            PermissionScreen {
                navController.navigate(Drawer)
            }
        }
        composable<Drawer> {
            DrawerScreen()
        }
    }
}