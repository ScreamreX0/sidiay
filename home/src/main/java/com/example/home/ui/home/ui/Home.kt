package com.example.home.ui.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.Screens
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.models.entities.TicketEntity
import com.example.domain.models.params.AuthParams
import com.example.home.ui.home.TicketsListViewModel
import com.example.home.ui.home.ui.components.list.TicketsListComponent
import com.example.home.ui.home.ui.components.top_bar.SearchComponent


class Home {
    @Composable
    fun HomeScreen(
        ticketsListViewModel: TicketsListViewModel = hiltViewModel(),
        navController: NavHostController = rememberNavController(),
        paddingValues: PaddingValues = PaddingValues(),
        authParams: MutableState<AuthParams?> = remember { mutableStateOf(AuthParams()) },
    ) {
        val tickets = ticketsListViewModel.tickets

        AppTheme {
            Content(
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
                navController = navController,
                authParams = authParams,
                tickets = tickets
            )
        }
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        authParams: MutableState<AuthParams?> = remember { mutableStateOf(AuthParams()) },
        tickets: MutableState<List<TicketEntity>> = remember { mutableStateOf(listOf()) },
        isSearchEnabled: MutableState<Boolean> = remember { mutableStateOf(false) },
        searchText: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    ) {
        Column(modifier = modifier.fillMaxSize()) {
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
                    return@TopAppBar
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { isSearchEnabled.value = true }) {
                        Icon(
                            painter = painterResource(id = com.example.core.R.drawable.ic_baseline_search_24_white),
                            contentDescription = "Search in tickets list",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }

                    IconButton(onClick = { navController.navigate(Screens.Home.TICKET_FILTER) }) {
                        Icon(
                            painter = painterResource(id = com.example.core.R.drawable.ic_baseline_filter_list_24_white),
                            contentDescription = "Filter tickets list",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }

                    IconButton(onClick = { navController.navigate(Screens.Home.TICKET_CREATE) }) {
                        Icon(
                            painter = painterResource(id = com.example.core.R.drawable.ic_baseline_add_24_white),
                            contentDescription = "Refresh tickets list",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }

            TicketsListComponent().Content(
                navController = navController,
                authParams = authParams,
                tickets = tickets,
            )
        }
    }

    /** Preview */
    @ScreenPreview
    @Composable
    private fun TicketsListPreview() {
        AppTheme { Content() }
    }
}