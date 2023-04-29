package com.example.signin.navigation

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Graphs
import com.example.core.ui.theme.AppTheme
import com.example.domain.data_classes.params.AuthParams
import com.example.signin.ui.SignIn
import com.google.gson.Gson

fun NavGraphBuilder.authenticationGraph(navController: NavHostController) {
    navigation(
        route = Graphs.AUTHENTICATION,
        startDestination = Authentication.SignInScreen.route
    ) {
        composable(route = Authentication.SignInScreen.route) {
            SignIn().SignInScreen(
                navigateToMainMenu = { authParamsString: String -> navController.navigate(route = "${Graphs.MAIN_MENU}/$authParamsString") },
                navigateToMainMenuOfflineMode = {
                    navController.popBackStack()
                    val authParams = Uri.encode(Gson().toJson(AuthParams()))
                    navController.navigate(route = "${Graphs.MAIN_MENU}/$authParams")
                }
            )
        }
    }
}

sealed class Authentication(val route: String) {
    object SignInScreen : Authentication(route = "SIGN_IN_SCREEN")
}