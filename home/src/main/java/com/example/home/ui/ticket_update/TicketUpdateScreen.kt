package com.example.home.ui.ticket_update

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.navigation.BottomBarNav
import com.example.domain.data_classes.params.AuthParams


class TicketUpdate {
    @Composable
    fun TicketUpdateScreen(
        navController: NavHostController,
        authParams: AuthParams?,
        ticketUpdateViewModel: TicketUpdateViewModel = hiltViewModel(),
    ) {
        Content(navController = navController, authParams = authParams)
    }

    @Composable
    private fun Content(
        navController: NavHostController,
        authParams: AuthParams?
    ) {
        Text(
            modifier = Modifier
                .clickable {
                    navController.popBackStack(
                        BottomBarNav.Home.route,
                        inclusive = false
                    )
                },
            text = "Ticket update")

    }
}