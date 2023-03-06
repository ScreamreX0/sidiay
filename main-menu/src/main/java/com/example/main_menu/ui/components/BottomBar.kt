package com.example.main_menu.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.core.navigation.BottomBarNav

internal class BottomBar {
    @Composable
    fun Content(navController: NavHostController) {
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
            BottomNavigation(
                contentColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background
            ) {
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
    private fun RowScope.AddItem(
        screen: BottomBarNav,
        currentDestination: NavDestination?,
        navController: NavHostController
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    painterResource(screen.icon),
                    contentDescription = null
                )
            },
            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
            unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
            onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    }
}