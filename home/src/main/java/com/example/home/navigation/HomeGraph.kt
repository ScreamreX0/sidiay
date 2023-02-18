package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Graphs
import com.example.home.ui.TicketsListScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = Graphs.HOME,
        startDestination = Home.TicketsListScreen.route
    ) {
        composable(route = Home.TicketsListScreen.route) {
            navController.navigate(Home.TicketsListScreen.route)
        }
//        composable(route = Home.TicketInfoScreen.route) {
//
//        }
//        composable(route = Home.TicketCreateScreen.route) {
//
//        }
    }
}

sealed class Home(val route: String) {
    object TicketsListScreen : Home(route = "TICKETS_LIST_SCREEN")
    object TicketInfoScreen : Home(route = "TICKET_INFO_SCREEN")
    object TicketCreateScreen : Home(route = "TICKET_CREATE_SCREEN")
}