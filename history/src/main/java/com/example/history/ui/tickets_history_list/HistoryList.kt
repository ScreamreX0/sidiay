package com.example.history.ui.tickets_history_list

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Constants
import com.example.core.utils.ScreenPreview
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.history.ui.tickets_history_list.components.HistoryTicketsList


class HistoryList {

    @Composable
    fun HistoryListScreen(
        historyListViewModel: HistoryListViewModel = hiltViewModel(),
        authParams: AuthParams? = remember { AuthParams() },
        navigateToTicketHistory: (TicketEntity) -> Unit,
    ) {
        LaunchedEffect(key1 = null) {
            historyListViewModel.fetchTicketsHistory(
                authParams?.connectionParams?.url,
                authParams?.user?.id
            )
        }

        Content(
            modifier = Modifier.padding(
                top = 55.dp,
                bottom = 50.dp
            ),
            ticketsHistoryFetchingState = historyListViewModel.ticketsHistoryFetchingState,
            ticketsHistory = historyListViewModel.ticketsHistory,
            navigateToTicketHistory = navigateToTicketHistory
        )
    }

    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        ticketsHistoryFetchingState: MutableState<INetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT),
        ticketsHistory: MutableState<List<TicketEntity>?> = mutableStateOf(null),
        navigateToTicketHistory: (TicketEntity) -> Unit = { _ -> },
    ) {
        TopBar()

        // Tickets history receiving progress
        if ((ticketsHistoryFetchingState.value == NetworkState.LOADING
                    || ticketsHistoryFetchingState.value == NetworkState.WAIT_FOR_INIT)
            && Constants.APPLICATION_MODE != ApplicationModes.OFFLINE
        ) {
            Row(
                modifier = modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) { CircularProgressIndicator(color = MaterialTheme.colors.primary) }
            return
        }

        Column(modifier = modifier.fillMaxSize()) {
            HistoryTicketsList().HistoryTicketsListScreen(
                tickets = ticketsHistory.value,
                onClick = navigateToTicketHistory,
            )
        }
    }

    @Composable
    private fun TopBar() {
        Row(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "История",
                fontSize = MaterialTheme.typography.h4.fontSize,
                color = MaterialTheme.colors.onBackground
            )
        }
    }

    @ScreenPreview
    @Composable
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) { Content() }
    }
}