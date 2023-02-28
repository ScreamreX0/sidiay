package com.example.main_menu.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.core.navigation.Graphs
import com.example.home.navigation.Home
import com.example.home.ui.ticket_update.TicketInfoScreen
import com.example.home.ui.tickets_list.ui.TicketsList
import com.example.notifications.navigation.Notifications
import com.example.notifications.ui.NotificationsScreen
import com.example.scanner.navigation.Scanner
import com.example.scanner.ui.ScannerScreen
import com.example.settings.navigation.Settings
import com.example.settings.ui.SettingsScreen

@Composable
fun MainMenuGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graphs.MAIN_MENU,
        startDestination = Home.TicketsListScreen.route
    ) {
        composable(route = Home.TicketsListScreen.route) {
            TicketsList.Screen(navController)
        }
        composable(route = Scanner.ScannerScreen.route) {
            ScannerScreen()
        }
        composable(route = Notifications.NotificationsScreen.route) {
            NotificationsScreen()
        }
        composable(route = Settings.SettingsScreen.route) {
            SettingsScreen()
        }
        homeNavGraph(navController)
    }
}

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        route = Graphs.HOME,
        startDestination = Home.TicketInfoScreen.route
    ) {
        composable(route = Home.TicketInfoScreen.route) {
            TicketInfoScreen()
        }
    }
}

sealed class MainMenu(val route: String) {
    object MainMenuScreen : MainMenu(route = "MAIN_MENU_SCREEN")
}