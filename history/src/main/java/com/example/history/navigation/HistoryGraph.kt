package com.example.history.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.history.ui.HistoryScreen

@Composable
fun HistoryGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = History.HistoryScreen.route
    ) {
        composable(route = History.HistoryScreen.route) {
            HistoryScreen()
        }
    }
}

sealed class History(val route: String) {
    object HistoryScreen : History(route = "SCANNER_SCREEN")
}