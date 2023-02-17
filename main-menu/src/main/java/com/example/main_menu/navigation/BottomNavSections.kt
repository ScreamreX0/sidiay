package com.example.main_menu.navigation

import androidx.annotation.StringRes
import com.example.core.navigation.Destinations

enum class BottomNavSections(
    @StringRes val title: Int,
    val route: String
) {
    HOME(com.example.home.R.navigation.home_graph, "${Destinations.MAIN_MENU}/${Destinations.BottomNav.HOME}"),
    SCANNER(com.example.scanner.R.navigation.scanner_graph, "${Destinations.MAIN_MENU}/${Destinations.BottomNav.SCANNER}"),
    NOTIFICATIONS(com.example.notifications.R.navigation.notifications_graph, "${Destinations.MAIN_MENU}/${Destinations.BottomNav.NOTIFICATIONS}"),
    SETTINGS(com.example.settings.R.navigation.settings_graph, "${Destinations.MAIN_MENU}/${Destinations.BottomNav.SETTINGS}")
}