package com.example.mapsapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val route: Destination
) {
    HOME(Icons.Default.LocationOn, "Map", Destination.Map),
    List(Icons.AutoMirrored.Filled.List, "List", Destination.List),
}