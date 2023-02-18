package com.example.notifications.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notifications.ui.NotificationsScreen

@Composable
fun NotificationsGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Notifications.NotificationsScreen.route
    ) {
        composable(route = Notifications.NotificationsScreen.route) {
            NotificationsScreen()
        }
    }
}

sealed class Notifications(val route: String) {
    object NotificationsScreen : Notifications(route = "NOTIFICATIONS_SCREEN")
}