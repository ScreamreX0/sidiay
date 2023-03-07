package com.example.home.ui.tickets_list.ui.components.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.domain.models.entities.TicketEntity
import com.example.home.ui.tickets_list.ui.components.list.components.TicketsListItemComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KMutableProperty0

internal class TicketsListComponent {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Content(
        navController: NavHostController,
        isDarkTheme: Boolean,
        tickets: KMutableProperty0<MutableState<List<TicketEntity>>>,
        refreshing: MutableState<Boolean> = remember { mutableStateOf(false) }
    ) {
        /** Refreshing */
        val refreshScope = rememberCoroutineScope()
        fun refresh() = refreshScope.launch {
            TODO("Refreshing tickets list")
            refreshing.value = true
            delay(1500)
            refreshing.value = false
        }

        val state = rememberPullRefreshState(refreshing.value, ::refresh)

        /** List */
        val ticketsList = tickets.get()
        Box(
            modifier = Modifier
                .pullRefresh(state)
                .zIndex(-1F),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                userScrollEnabled = true,
            ) {
                if (!refreshing.value) {
                    items(ticketsList.value.size) {
                        TicketsListItemComponent.Content(
                            navController,
                            isDarkTheme = isDarkTheme,
                            ticket = ticketsList.value[it]
                        )
                    }
                }
            }
            PullRefreshIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                refreshing = refreshing.value,
                state = state,
                contentColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background
            )
        }
    }
}