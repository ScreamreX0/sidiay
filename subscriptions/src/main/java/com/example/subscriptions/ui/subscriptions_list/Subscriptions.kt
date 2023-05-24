package com.example.subscriptions.ui.subscriptions_list

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.subscriptions.ui.subscriptions_list.components.SubscriptionsList


class Subscriptions {

    @Composable
    fun NotificationsListScreen(
        subscriptionsViewModel: SubscriptionsViewModel = hiltViewModel(),
        authParams: AuthParams? = remember { AuthParams() },
    ) {
        LaunchedEffect(key1 = null) {
            subscriptionsViewModel.fetchNotifications(
                authParams?.connectionParams?.url,
                authParams?.user?.id
            )
        }

        Content(
            modifier = Modifier.padding(
                top = 55.dp,
                bottom = 50.dp
            ),
            ticketsHistoryFetchingState = subscriptionsViewModel.notificationsFetchingState,
            ticketsHistory = subscriptionsViewModel.notifications,
            unsubscribe = { subscriptionsViewModel.unsubscribe(authParams?.connectionParams?.url, authParams?.user?.id, it) },
        )
    }

    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        ticketsHistoryFetchingState: MutableState<INetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT),
        ticketsHistory: MutableState<List<TicketEntity>?> = mutableStateOf(null),
        unsubscribe: (TicketEntity) -> Unit = { }
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
            SubscriptionsList().NotificationsListScreen(
                tickets = ticketsHistory.value,
                onClick = unsubscribe
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
                text = "Подписки",
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