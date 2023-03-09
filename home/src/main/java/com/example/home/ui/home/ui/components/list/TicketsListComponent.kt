package com.example.home.ui.home.ui.components.list

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
import androidx.navigation.compose.rememberNavController
import com.example.domain.models.entities.TicketEntity
import com.example.domain.models.params.AuthParams
import kotlinx.coroutines.launch

internal class TicketsListComponent {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Content(
        navController: NavHostController = rememberNavController(),
        authParams: MutableState<AuthParams?> = remember { mutableStateOf(AuthParams()) },
        tickets: MutableState<List<TicketEntity>> = remember { mutableStateOf(listOf()) },
        refreshing: MutableState<Boolean> = remember { mutableStateOf(false) },
    ) {
        /** Refreshing */
        val scope = rememberCoroutineScope()
        fun refresh() = scope.launch {
            TODO("Refreshing tickets list")
            //refreshing.value = true
            //delay(1500)
            //refreshing.value = false
        }

        val state = rememberPullRefreshState(refreshing.value, ::refresh)

        /** List */
        Box(
            modifier = Modifier
                .pullRefresh(state)
                .zIndex(-1F),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = true,
            ) {
                if (!refreshing.value) {
                    items(tickets.value.size) {
                        TicketsListItemComponent().Content(
                            navController,
                            isDarkTheme = authParams.value?.darkMode ?: false,
                            ticket = tickets.value[it],
                        )
                    }
                }
            }
            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = refreshing.value,
                state = state,
                contentColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background
            )
        }
    }
}