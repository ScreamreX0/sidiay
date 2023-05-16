package com.example.core.navigation

sealed class BottomBarNav(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home : BottomBarNav(
        route = "BOTTOM_BAR_HOME_SCREEN",
        title = "home",
        icon = com.example.core.R.drawable.ic_baseline_menu_24,
    )

    object Notifications : BottomBarNav(
        route = "BOTTOM_BAR_NOTIFICATIONS_SCREEN",
        title = "notifications",
        icon = com.example.core.R.drawable.ic_baseline_notifications_24
    )

    object History : BottomBarNav(
        route = "BOTTOM_BAR_HISTORY_SCREEN",
        title = "history",
        icon = com.example.core.R.drawable.baseline_history_24
    )

    object Settings : BottomBarNav(
        route = "BOTTOM_BAR_SETTINGS_SCREEN",
        title = "settings",
        icon = com.example.core.R.drawable.ic_baseline_settings_24
    )
}