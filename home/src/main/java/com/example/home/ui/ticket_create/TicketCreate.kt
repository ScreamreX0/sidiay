package com.example.home.ui.ticket_create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.BottomBarNav
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.enums.MainMenuTopAppBarEnum


class TicketCreate {
    @Composable
    fun TicketCreateScreen(
        navController: NavHostController,
        authParams: AuthParams?,
        ticketCreateViewModel: TicketCreateViewModel = hiltViewModel()
    ) {
        Content(
            navController = navController,
            authParams = authParams
        )
    }

    @Composable
    private fun Content(
        navController: NavHostController = rememberNavController(),
        authParams: AuthParams? = AuthParams(),
    ) {

        TopAppBar(
            contentColor = MaterialTheme.colors.onBackground,
            backgroundColor = MaterialTheme.colors.background,
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {

            }
        }
    }


    @Composable
    @ScreenPreview
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) {
            Content()
        }
    }
}
