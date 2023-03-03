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

    object Scanner : BottomBarNav(
        route = "BOTTOM_BAR_SCANNER_SCREEN",
        title = "scanner",
        icon = com.example.core.R.drawable.ic_baseline_qr_code_scanner_24
    )

    object Notifications : BottomBarNav(
        route = "BOTTOM_BAR_NOTIFICATIONS_SCREEN",
        title = "notifications",
        icon = com.example.core.R.drawable.ic_baseline_notifications_24
    )

    object Settings : BottomBarNav(
        route = "BOTTOM_BAR_SETTINGS_SCREEN",
        title = "settings",
        icon = com.example.core.R.drawable.ic_baseline_settings_24
    )
}