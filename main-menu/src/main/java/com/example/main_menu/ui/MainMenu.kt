package com.example.main_menu.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.BottomBarNav
import com.example.core.ui.theme.AppTheme
import com.example.main_menu.navigation.HomeNavGraph


class MainMenu {
    companion object {
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun Content(
            isDarkTheme: MutableState<Boolean> = remember { mutableStateOf(false) },
            navController: NavHostController = rememberNavController()
        ) {
            AppTheme(isDarkTheme.value) {
                Scaffold(
                    bottomBar = { BottomBar(navController = navController) }
                ) {
                    Modifier.padding(it)
                    HomeNavGraph(navController = navController)
                }
            }
        }

        @Composable
        internal fun BottomBar(
            navController: NavHostController
        ) {
            val screens = listOf(
                BottomBarNav.Home,
                BottomBarNav.Scanner,
                BottomBarNav.Notifications,
                BottomBarNav.Settings,
            )

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val bottomBarDestination = screens.any { it.route == currentDestination?.route }
            if (bottomBarDestination) {
                BottomNavigation {
                    screens.forEach { screen ->
                        AddItem(
                            screen = screen,
                            currentDestination = currentDestination,
                            navController = navController
                        )
                    }
                }
            }
        }

        @Composable
        internal fun RowScope.AddItem(
            screen: BottomBarNav,
            currentDestination: NavDestination?,
            navController: NavHostController
        ) {
            BottomNavigationItem(
                label = { Text(text = screen.title) },
                icon = {
                    Icon(
                        painterResource(screen.icon),
                        contentDescription = null
                    )
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                })
        }
    }
}

