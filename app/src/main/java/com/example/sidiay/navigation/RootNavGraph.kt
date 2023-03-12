package com.example.sidiay.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.navigation.Graphs
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.types.AuthParamsType
import com.example.main_menu.ui.MainMenu
import com.example.signin.navigation.authenticationGraph


@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        route = Graphs.ROOT,
        navController = navController,
        startDestination = Graphs.AUTHENTICATION
    ) {
        authenticationGraph(navController = navController)
        composable(
            route = "${Graphs.MAIN_MENU}/{params}",
            arguments = listOf(navArgument("params") { type = AuthParamsType() })
        ) {
            MainMenu().Content(
                authParams = it.arguments?.getParcelable("params") ?: AuthParams()
            )
        }
    }
}