package com.example.home.ui.tickets_list.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.models.entities.TicketEntity
import com.example.home.ui.tickets_list.TicketsListViewModel
import com.example.home.ui.tickets_list.ui.components.TicketsListItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KMutableProperty0


class TicketsList {
    @Composable
    fun TicketsListScreen(
        ticketsListViewModel: TicketsListViewModel = hiltViewModel(),
        navController: NavHostController = rememberNavController(),
        isDarkTheme: MutableState<Boolean> = remember { mutableStateOf(false) },
        paddingValues: PaddingValues = PaddingValues()
    ) {
        AppTheme {
            Content(
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
                navController = navController,
                isDarkTheme = isDarkTheme.value,
                tickets = ticketsListViewModel::tickets
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        navController: NavHostController,
        isDarkTheme: Boolean,
        tickets: KMutableProperty0<MutableState<List<TicketEntity>>>
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                contentColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = com.example.core.R.drawable.ic_baseline_search_24_white),
                            contentDescription = "Search in tickets list",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = com.example.core.R.drawable.baseline_expand_24),
                            contentDescription = "Expand tickets list items",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = com.example.core.R.drawable.ic_baseline_filter_list_24_white),
                            contentDescription = "Filter tickets list",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = com.example.core.R.drawable.ic_baseline_add_24_white),
                            contentDescription = "Refresh tickets list",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }

            val refreshScope = rememberCoroutineScope()
            var refreshing by remember { mutableStateOf(false) }

            fun refresh() = refreshScope.launch {
                refreshing = true
                delay(1500)
                refreshing = false
            }
            val state = rememberPullRefreshState(refreshing, ::refresh)
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
                    if (!refreshing) {
                        items(tickets.get().value.size) {
                            TicketsListItem.Content(
                                navController,
                                isDarkTheme = isDarkTheme
                            )
                        }
                    }
                }
                PullRefreshIndicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    refreshing = refreshing,
                    state = state,
                    contentColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background
                )
            }
        }
    }

    var tickets: MutableState<List<TicketEntity>> = mutableStateOf(listOf())

    @ScreenPreview
    @Composable
    private fun TicketsListPreview() {
        AppTheme {
            Content(
                navController = rememberNavController(),
                isDarkTheme = false,
                tickets = this::tickets
            )
        }
    }
}