package com.example.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.BottomBarNav
import com.example.core.navigation.Graphs
import com.example.home.ui.tickets_list.ui.TicketsList

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = Graphs.HOME,
        startDestination = "TestDest"
    ) {
        composable(route = "TestDest") {
            //navController.navigate(BottomBarNav.Home.route)
        }
    }
}

