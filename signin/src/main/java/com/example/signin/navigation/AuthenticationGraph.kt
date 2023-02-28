package com.example.signin.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Graphs
import com.example.signin.ui.signin.SignIn

fun NavGraphBuilder.authenticationGraph(navController: NavHostController) {
    navigation(
        route = Graphs.SIGN_IN,
        startDestination = Authentication.SignInScreen.route
    ) {
        composable(route = Authentication.SignInScreen.route) {
            SignIn().SignInScreen(navController = navController)
        }
    }
}

sealed class Authentication(val route: String) {
    object SignInScreen : Authentication(route = "SIGN_IN_SCREEN")
}