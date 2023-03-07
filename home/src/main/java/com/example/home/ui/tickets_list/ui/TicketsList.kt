package com.example.home.ui.tickets_list.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.models.entities.TicketEntity
import com.example.home.ui.tickets_list.TicketsListViewModel
import com.example.home.ui.tickets_list.ui.components.list.TicketsListComponent
import com.example.home.ui.tickets_list.ui.components.top_bar.SearchComponent
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

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        navController: NavHostController,
        isDarkTheme: Boolean,
        tickets: KMutableProperty0<MutableState<List<TicketEntity>>>,
        isSearchEnabled: MutableState<Boolean> = remember { mutableStateOf(false) },
        searchText: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            /** App bar */
            TopAppBar(
                contentColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background,
            ) {
                if (isSearchEnabled.value) {
                    SearchComponent().Content(
                        isSearchEnabled = isSearchEnabled,
                        textState = searchText,
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(onClick = { isSearchEnabled.value = true }) {
                            Icon(
                                painter = painterResource(id = com.example.core.R.drawable.ic_baseline_search_24_white),
                                contentDescription = "Search in tickets list",
                                tint = MaterialTheme.colors.onBackground
                            )
                        }

                        IconButton(onClick = { TODO("Expand tickets") }) {
                            Icon(
                                painter = painterResource(id = com.example.core.R.drawable.baseline_expand_24),
                                contentDescription = "Expand tickets list items",
                                tint = MaterialTheme.colors.onBackground
                            )
                        }

                        IconButton(onClick = { TODO("Filter tickets") }) {
                            Icon(
                                painter = painterResource(id = com.example.core.R.drawable.ic_baseline_filter_list_24_white),
                                contentDescription = "Filter tickets list",
                                tint = MaterialTheme.colors.onBackground
                            )
                        }

                        IconButton(onClick = { TODO("Add ticket") }) {
                            Icon(
                                painter = painterResource(id = com.example.core.R.drawable.ic_baseline_add_24_white),
                                contentDescription = "Refresh tickets list",
                                tint = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                }
            }

            TicketsListComponent().Content(
                navController = navController,
                isDarkTheme = isDarkTheme,
                tickets = tickets,
            )
        }
    }

    private var tickets: MutableState<List<TicketEntity>> = mutableStateOf(listOf())

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