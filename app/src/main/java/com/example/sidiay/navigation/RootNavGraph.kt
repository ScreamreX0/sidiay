package com.example.sidiay.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core.navigation.Destinations
import com.example.main_menu.ui.MainMenuScreen
import com.example.signin.ui.SignInScreen


@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.SIGN_IN
    ) {
        composable(route = Destinations.SIGN_IN) {
            SignInScreen(navController = navController)
        }
        composable(
            route = Destinations.MAIN_MENU,
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.LongType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("userFirstName") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("userName") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("userLastName") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("userEmail") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("userPassword") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                },
                navArgument("userPhone") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("userPhoto") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) {
            MainMenuScreen()
        }
    }
}