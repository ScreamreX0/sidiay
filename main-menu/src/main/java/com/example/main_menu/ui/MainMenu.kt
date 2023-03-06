package com.example.main_menu.ui

import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.models.entities.UserEntity
import com.example.main_menu.navigation.HomeNavGraph
import com.example.main_menu.ui.components.BottomBar


class MainMenu {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(
        isDarkTheme: MutableState<Boolean> = remember { mutableStateOf(false) },
        navController: NavHostController = rememberNavController(),
        user: UserEntity? = UserEntity()
    ) {
        AppTheme(isDarkTheme.value) {
            Scaffold(
                bottomBar = { BottomBar().Content(navController = navController) },
            ) {
                HomeNavGraph(
                    paddingValues = it,
                    navController = navController
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

