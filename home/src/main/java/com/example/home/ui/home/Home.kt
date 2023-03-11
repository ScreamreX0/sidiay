package com.example.home.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.Screens
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.DefaultTextStyle
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.data_classes.entities.DraftEntity
import com.example.domain.enums.MainMenuTabEnum
import com.example.domain.enums.MainMenuTopAppBarEnum
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.home.ui.drafts.DraftsComponent
import com.example.home.ui.home.components.list.TicketsListComponent
import com.example.home.ui.home.components.top_bar.SearchComponent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState


class Home {
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun HomeScreen(
        homeViewModel: HomeViewModel = hiltViewModel(),
        navController: NavHostController = rememberNavController(),
        paddingValues: PaddingValues = PaddingValues(),
        authParams: MutableState<AuthParams?> = remember { mutableStateOf(AuthParams()) },
    ) {
        val tickets = homeViewModel.tickets
        val drafts = homeViewModel.drafts

        AppTheme {
            Content(
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
                navController = navController,
                authParams = authParams,
                tickets = tickets,
                drafts = drafts,
            )
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        authParams: MutableState<AuthParams?> = remember { mutableStateOf(AuthParams()) },
        tickets: MutableState<List<TicketEntity>> = remember { mutableStateOf(listOf()) },
        drafts: MutableState<List<DraftEntity>> = remember { mutableStateOf(listOf()) },
        isSearchEnabled: MutableState<Boolean> = remember { mutableStateOf(false) },
        searchText: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
        selectedTab: MutableState<MainMenuTabEnum> = remember { mutableStateOf(MainMenuTabEnum.TICKETS) },
        selectedTopApp: MutableState<MainMenuTopAppBarEnum?> = remember { mutableStateOf(null) },
        pagerState: PagerState = rememberPagerState()
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
                    MainMenuTopAppBarEnum.values().forEach {
                        IconButton(onClick = { selectedTopApp.value = it }) {
                            Icon(
                                painter = painterResource(id = it.icon),
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            when (selectedTopApp.value?.id) {
                0 -> isSearchEnabled.value = true  // Search
                1 -> navController.navigate(Screens.Home.TICKET_FILTER)  // Filter
                2 -> navController.navigate(Screens.Home.TICKET_CREATE)  // Create
            }

            /** Tabs */
            TabRow(
                modifier = Modifier.height(40.dp),
                backgroundColor = MaterialTheme.colors.background,
                selectedTabIndex = pagerState.currentPage,
            ) {
                MainMenuTabEnum.values().forEachIndexed { index, it ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {

                        }) {
                        DefaultTextStyle {
                            Text(
                                text = it.title,
                                color = MaterialTheme.colors.onBackground,
                            )
                        }
                    }
                }
            }

            HorizontalPager(
                count = MainMenuTabEnum.values().size,
                state = pagerState,
            ) {
                if (it == 0) {
                    TicketsListComponent().Content(
                        navController = navController,
                        authParams = authParams,
                        tickets = tickets,
                    )
                } else if (it == 1) {
                    DraftsComponent().Content(
                        navController = navController,
                        authParams = authParams,
                        drafts = drafts,
                    )
                }
            }

        }
    }

    /** Preview */
    @OptIn(ExperimentalPagerApi::class)
    @ScreenPreview
    @Composable
    private fun TicketsListPreview() {
        AppTheme { Content() }
    }
}