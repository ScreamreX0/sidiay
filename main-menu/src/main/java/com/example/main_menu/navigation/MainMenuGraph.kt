package com.example.main_menu.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.core.navigation.BottomBarNav
import com.example.core.navigation.Graphs
import com.example.core.navigation.Screens
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.home.ui.home.Home
import com.example.home.ui.ticket_create.TicketCreate
import com.example.home.ui.ticket_update.TicketUpdate
import com.example.home.ui.tickets_filter.TicketFilter

@Composable
fun MainMenuGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    authParams: AuthParams
) {
    NavHost(
        navController = navController,
        route = Graphs.MAIN_MENU,
        startDestination = Graphs.HOME
    ) {
        composable(route = BottomBarNav.Scanner.route) {

        }
        composable(route = BottomBarNav.Notifications.route) {

        }
        composable(route = BottomBarNav.Settings.route) {

        }
        homeNavGraph(
            navController = navController,
            authParams = authParams,
            paddingValues = paddingValues,
        )
    }
}

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    authParams: AuthParams,
    paddingValues: PaddingValues,
) {
    navigation(
        route = Graphs.HOME,
        startDestination = BottomBarNav.Home.route
    ) {
        composable(route = BottomBarNav.Home.route) {
            AppTheme(authParams.darkMode ?: false) {
                Home().HomeScreen(
                    navController = navController,
                    authParams = remember { authParams },
                    paddingValues = paddingValues,
                )
            }
        }
        composable(
            route = "${Screens.Home.TICKET_UPDATE}/{ticket}",
            arguments = listOf(navArgument("ticket") { type = NavType.StringType })
        ) {
            AppTheme(authParams.darkMode ?: false) {
                TicketUpdate().TicketUpdateScreen(
                    navController = navController,
                    authParams = authParams,
                    ticket = Helper.objFromJson(it.arguments?.getString("ticket")) ?: TicketEntity()
                )
            }
        }
        composable(route = Screens.Home.TICKET_CREATE) {
            AppTheme(authParams.darkMode ?: false) {
                TicketCreate().TicketCreateScreen(
                    navController = navController,
                    authParams = authParams,
                )
            }
        }
        composable(route = Screens.Home.TICKET_FILTER) {
            AppTheme(authParams.darkMode ?: false) {
                TicketFilter().TicketFilterScreen(
                    navController = navController,
                    authParams = authParams,
                )
            }
        }
    }
}



