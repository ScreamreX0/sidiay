package com.example.home.ui.tickets_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.*
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.MainMenuTabEnum
import com.example.domain.enums.MainMenuTopAppBarEnum
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.enums.states.NetworkState
import com.example.home.ui.tickets_list.components.MenuTicketList
import com.example.home.ui.tickets_list.components.MenuSearch
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


class TicketsList {
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun HomeScreen(
        ticketsListViewModel: TicketsListViewModel = hiltViewModel(),
        paddingValues: PaddingValues = remember { PaddingValues() },
        authParams: AuthParams? = remember { AuthParams() },
        navigateToTicketFilter: () -> Unit,
        navigateToTicketCreate: () -> Unit,
        navigateToTicketUpdate: (TicketEntity) -> Unit,
    ) {
        val ticketsReceivingState = ticketsListViewModel.ticketsReceivingState
        val ticketsForExecution = ticketsListViewModel.ticketsForExecution
        val ticketsPersonal = ticketsListViewModel.ticketsPersonal
        val errorMessage = ticketsListViewModel.errorMessage
        val drafts = ticketsListViewModel.drafts
        val context = LocalContext.current

        // Receiving tickets
        LaunchedEffect(key1 = null) {
            ticketsListViewModel.fetchTickets(
                url = authParams?.connectionParams?.url,
                userId = authParams?.user?.id ?: 0
            )
        }

        errorMessage.value?.let { Helper.showShortToast(context = context, text = it.toString()) }

        Content(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 50.dp
            ),
            authParams = authParams,
            drafts = drafts,
            fetchTickets = ticketsListViewModel::fetchTickets,
            navigateToTicketFilter = navigateToTicketFilter,
            navigateToTicketCreate = navigateToTicketCreate,
            navigateToTicketUpdate = navigateToTicketUpdate,
            ticketsReceivingState = ticketsReceivingState,
            ticketsForExecution = ticketsForExecution,
            ticketsPersonal = ticketsPersonal,
        )
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        authParams: AuthParams? = remember { AuthParams() },
        drafts: MutableState<List<TicketEntity>> = mutableStateOf(listOf()),
        isSearchEnabled: MutableState<Boolean> = remember { mutableStateOf(false) },
        searchText: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
        pagerState: PagerState = rememberPagerState(),
        fetchTickets: (url: String?, userId: Long) -> Unit = { _, _ -> },
        navigateToTicketFilter: () -> Unit = {},
        navigateToTicketCreate: () -> Unit = {},
        navigateToTicketUpdate: (TicketEntity) -> Unit = { _ -> },
        ticketsReceivingState: MutableState<NetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT),
        ticketsForExecution: MutableState<List<TicketEntity>?> = mutableStateOf(null),
        ticketsPersonal: MutableState<List<TicketEntity>?> = mutableStateOf(null),
    ) {
        //
        // TICKETS LOADING
        //
        if ((ticketsReceivingState.value == NetworkState.LOADING || ticketsReceivingState.value == NetworkState.WAIT_FOR_INIT)
            && ConstAndVars.APPLICATION_MODE != ApplicationModes.DEBUG_AND_OFFLINE
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) { CircularProgressIndicator(color = MaterialTheme.colors.primary) }
            return
        }

        Column(modifier = modifier.fillMaxSize()) {
            // App bar
            TopAppBar(
                contentColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background
            ) {
                if (isSearchEnabled.value) {
                    MenuSearch(
                        isSearchEnabled = isSearchEnabled,
                        textState = searchText,
                    )
                    return@TopAppBar
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        modifier = Modifier.clickable { isSearchEnabled.value = true },
                        painter = painterResource(id = MainMenuTopAppBarEnum.SEARCH.icon),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier.clickable { navigateToTicketFilter() },
                        painter = painterResource(id = MainMenuTopAppBarEnum.FILTER.icon),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier.clickable { navigateToTicketCreate() },
                        painter = painterResource(id = MainMenuTopAppBarEnum.CREATE.icon),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            fetchTickets(
                                authParams?.connectionParams?.url,
                                authParams?.user?.id ?: 0
                            )
                        },
                        painter = painterResource(id = MainMenuTopAppBarEnum.REFRESH.icon),
                        contentDescription = null
                    )
                }
            }

            // Tabs
            val scrollCoroutineScope = rememberCoroutineScope()
            ScrollableTabRow(
                modifier = Modifier.height(40.dp),
                selectedTabIndex = pagerState.currentPage,
                contentColor = MaterialTheme.colors.onBackground,
                edgePadding = 0.dp,
                tabs = {
                    MainMenuTabEnum.values().forEachIndexed { index, it ->
                        Tab(
                            modifier = Modifier.background(MaterialTheme.colors.background),
                            selected = pagerState.currentPage == index,
                            onClick = { scrollCoroutineScope.launch { pagerState.scrollToPage(index) } },
                            text = {
                                Text(
                                    text = it.title,
                                    color = MaterialTheme.colors.onBackground,
                                    fontSize = MaterialTheme.typography.h3.fontSize
                                )
                            })
                    }
                }
            )

            // Pages
            HorizontalPager(
                count = MainMenuTabEnum.values().size,
                state = pagerState,
            ) { pageNumber ->
                when (pageNumber) {

                    MainMenuTabEnum.USER_AUTHOR_TICKETS.id -> {
                        MenuTicketList(
                            authParams = authParams,
                            tickets = ticketsPersonal,
                            onClickUpdate = { itTicket -> navigateToTicketUpdate(itTicket) }
                        )
                    }

                    MainMenuTabEnum.USER_EXECUTOR_TICKETS.id -> {
                        MenuTicketList(
                            authParams = authParams,
                            tickets = ticketsForExecution,
                            onClickUpdate = { itTicket -> navigateToTicketUpdate(itTicket) }
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @ScreenPreview
    @Composable
    private fun TicketsListPreview() {
        AppTheme { Content() }
    }
}