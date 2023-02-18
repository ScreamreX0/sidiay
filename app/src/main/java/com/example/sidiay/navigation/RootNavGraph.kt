package com.example.sidiay.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.navigation.Graphs
import com.example.main_menu.ui.MainMenuScreen
import com.example.signin.navigation.Authentication
import com.example.signin.navigation.authenticationGraph


@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        route = Graphs.ROOT,
        navController = navController,
        startDestination = Graphs.SIGN_IN
    ) {
        authenticationGraph(navController = navController)
        composable(
            route = Graphs.MAIN_MENU,
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