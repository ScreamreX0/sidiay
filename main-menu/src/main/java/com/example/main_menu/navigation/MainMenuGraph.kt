package com.example.main_menu.navigation

import android.content.Context
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
import com.example.home.ui.tickets_list.TicketsList
import com.example.home.ui.ticket_create.TicketCreate
import com.example.home.ui.ticket_update.TicketUpdate
import com.example.home.ui.tickets_filter.TicketFilter
import com.example.settings.ui.SettingsScreen

@Composable
fun MainMenuGraph(
    navController: NavHostController,
    rootNavController: NavHostController,
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
        settingsNavGraph(
            authParams = authParams,
            logout = {
                rootNavController.navigate(Graphs.AUTHENTICATION)
            }
        )
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
                TicketsList().HomeScreen(
                    authParams = remember { authParams },
                    paddingValues = paddingValues,
                    navigateToTicketFilter = { navController.navigate(Screens.Home.TICKET_FILTER) },
                    navigateToTicketCreate = { navController.navigate(Screens.Home.TICKET_CREATE) },
                    navigateToTicketUpdate = { ticket: TicketEntity -> navController.navigate("${Screens.Home.TICKET_UPDATE}/${Helper.objToJson(ticket)}") }
                )
            }
        }
        composable(
            route = "${Screens.Home.TICKET_UPDATE}/{ticket}",
            arguments = listOf(navArgument("ticket") { type = NavType.StringType })
        ) {
            AppTheme(authParams.darkMode ?: false) {
                TicketUpdate().TicketUpdateScreen(
                    authParams = authParams,
                    ticket = Helper.objFromJson(it.arguments?.getString("ticket")) ?: TicketEntity(),
                    navigateToBackWithMessage = { context: Context ->
                        navController.popBackStack(
                            route = BottomBarNav.Home.route,
                            inclusive = false
                        )
                        Helper.showShortToast(context = context, text = "Заявка успешно сохранена")
                    },
                    navigateToBack = {
                        navController.popBackStack(
                            route = BottomBarNav.Home.route,
                            inclusive = false
                        )
                    }
                )
            }
        }
        composable(route = Screens.Home.TICKET_CREATE) {
            AppTheme(authParams.darkMode ?: false) {
                TicketCreate().TicketCreateScreen(
                    authParams = authParams,
                    navigateToBackWithMessage = { context: Context ->
                        navController.popBackStack(
                            route = BottomBarNav.Home.route,
                            inclusive = false
                        )
                        Helper.showShortToast(context = context, text = "Заявка успешно сохранена")
                    },
                    navigateToBack = {
                        navController.popBackStack(
                            route = BottomBarNav.Home.route,
                            inclusive = false
                        )
                    }
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


fun NavGraphBuilder.settingsNavGraph(
    authParams: AuthParams,
    logout: () -> Unit = {}
) {
    navigation(
        route = Graphs.SETTINGS,
        startDestination = BottomBarNav.Settings.route
    ) {
        composable(route = BottomBarNav.Settings.route) {
            AppTheme(authParams.darkMode ?: false) {
                SettingsScreen().Content(
                    authParams = authParams,
                    logout = { logout() }
                )
            }
        }
    }
}


