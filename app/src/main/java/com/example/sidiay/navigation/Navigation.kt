package com.example.sidiay.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.Destinations
import com.example.main_menu.ui.MainMenuScreen
import com.example.signin.ui.SignInScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.SIGN_IN
    ) {
        composable(route = Destinations.SIGN_IN) {
            SignInScreen(navController = navController)
        }

        composable(route = Destinations.MAIN_MENU) {
            MainMenuScreen(navController = navController)
        }
    }
}