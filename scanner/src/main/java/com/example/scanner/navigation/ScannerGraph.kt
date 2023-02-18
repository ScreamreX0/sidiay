package com.example.scanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.scanner.ui.ScannerScreen

@Composable
fun ScannerGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Scanner.ScannerScreen.route
    ) {
        composable(route = Scanner.ScannerScreen.route) {
            ScannerScreen()
        }
    }
}

sealed class Scanner(val route: String) {
    object ScannerScreen : Scanner(route = "SCANNER_SCREEN")
}