package com.example.main_menu.ui

import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.ScreenPreview
import com.example.domain.data_classes.params.AuthParams
import com.example.main_menu.navigation.MainMenuGraph
import com.example.main_menu.ui.components.BottomBar


class MainMenu {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(
        navController: NavHostController = rememberNavController(),
        authParams: AuthParams = AuthParams()
    ) {
        AppTheme(authParams.darkMode ?: false) {
            Scaffold(bottomBar = { BottomBar().Content(navController = navController) }) {
                MainMenuGraph(
                    paddingValues = it,
                    navController = navController,
                    authParams = authParams
                )
            }
        }
    }

    @ScreenPreview
    @Composable
    private fun Preview() {
        Content()
    }
}

