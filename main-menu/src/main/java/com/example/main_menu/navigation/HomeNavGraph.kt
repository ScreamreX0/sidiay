package com.example.main_menu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.Destinations
import com.example.home.ui.TicketsListScreen
import com.example.notifications.ui.NotificationsScreen
import com.example.scanner.ui.ScannerScreen
import com.example.settings.ui.SettingsScreen

@Composable
fun HomeNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.BottomNav.HOME
    ) {
        composable(route = Destinations.BottomNav.HOME) {
            TicketsListScreen()
        }

        composable(route = Destinations.BottomNav.SCANNER) {
            ScannerScreen()
        }

        composable(route = Destinations.BottomNav.NOTIFICATIONS) {
            NotificationsScreen()
        }

        composable(route = Destinations.BottomNav.SETTINGS) {
            SettingsScreen()
        }
    }
}