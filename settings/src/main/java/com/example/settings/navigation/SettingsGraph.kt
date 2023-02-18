package com.example.settings.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.settings.ui.SettingsScreen

@Composable
fun SettingsGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Settings.SettingsScreen.route
    ) {
        composable(route = Settings.SettingsScreen.route) {
            SettingsScreen()
        }
    }
}

sealed class Settings(val route: String) {
    object SettingsScreen : Settings(route = "SETTINGS_SCREEN")
}