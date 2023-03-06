package com.example.main_menu.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.navigation.BottomBarNav
import com.example.core.navigation.Graphs
import com.example.home.ui.ticket_create.TicketCreateScreen
import com.example.home.ui.ticket_update.TicketUpdateScreen
import com.example.home.ui.tickets_list.ui.TicketsList

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        route = Graphs.HOME,
        startDestination = BottomBarNav.Home.route
    ) {
        composable(route = BottomBarNav.Home.route) {
            TicketsList().TicketsListScreen(paddingValues = paddingValues)
        }
        composable(route = BottomBarNav.Scanner.route) {

        }
        composable(route = BottomBarNav.Notifications.route) {

        }
        composable(route = BottomBarNav.Settings.route) {

        }
        ticketsListNavGraph()
    }
}

fun NavGraphBuilder.ticketsListNavGraph() {
    navigation(
        route = Graphs.DETAILS,
        startDestination = TicketsListScreen.TicketUpdate.route
    ) {
        composable(route = TicketsListScreen.TicketUpdate.route) {
            TicketUpdateScreen().Content()
        }
        composable(route = TicketsListScreen.TicketCreate.route) {
            TicketCreateScreen().Content()
        }
    }
}

sealed class TicketsListScreen(val route: String) {
    object TicketUpdate : TicketsListScreen(route = "TICKET_INFO")
    object TicketCreate : TicketsListScreen(route = "TICKET_CREATE")
}

