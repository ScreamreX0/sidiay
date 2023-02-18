package com.example.main_menu.navigation

sealed class BottomBarNav(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home : BottomBarNav(
        route = com.example.home.navigation.Home.TicketsListScreen.route,
        title = "home",
        icon = com.example.core.R.drawable.ic_baseline_menu_24,
    )

    object Scanner : BottomBarNav(
        route = com.example.scanner.navigation.Scanner.ScannerScreen.route,
        title = "scanner",
        icon = com.example.core.R.drawable.ic_baseline_qr_code_scanner_24
    )

    object Notifications : BottomBarNav(
        route = com.example.notifications.navigation.Notifications.NotificationsScreen.route,
        title = "notifications",
        icon = com.example.core.R.drawable.ic_baseline_notifications_24
    )

    object Settings : BottomBarNav(
        route = com.example.settings.navigation.Settings.SettingsScreen.route,
        title = "settings",
        icon = com.example.core.R.drawable.ic_baseline_settings_24
    )
}