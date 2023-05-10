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
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.enums.MainMenuOfflineTabEnum
import com.example.domain.enums.MainMenuTabEnum
import com.example.domain.enums.MainMenuTopAppBarEnum
import com.example.domain.enums.states.NetworkState
import com.example.home.ui.tickets_list.components.CustomTabRow
import com.example.home.ui.tickets_list.components.MenuSearch
import com.example.home.ui.tickets_list.components.MenuTicketList
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
        val tickets = ticketsListViewModel.tickets

        val ticketsPersonal = ticketsListViewModel.ticketsPersonal
        val errorMessage = ticketsListViewModel.errorMessage
        val drafts = ticketsListViewModel.drafts
        val context = LocalContext.current

        // Fetching tickets
        LaunchedEffect(key1 = null) {
            ticketsListViewModel.fetchTickets(
                url = authParams?.connectionParams?.url,
                userId = authParams?.user?.id ?: 0
            )
        }
        // Fetching drafts
        LaunchedEffect(key1 = null) { ticketsListViewModel.fetchDrafts() }
        // Fetching ticket data
        LaunchedEffect(key1 = null) {
            ticketsListViewModel.fetchTicketData(authParams?.connectionParams?.url)
        }

        errorMessage.value?.let { Helper.showShortToast(context = context, text = it.toString()) }

        Content(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 50.dp
            ),
            authParams = authParams,
            fetchTickets = ticketsListViewModel::fetchTickets,
            navigateToTicketFilter = navigateToTicketFilter,
            navigateToTicketCreate = navigateToTicketCreate,
            navigateToTicketUpdate = navigateToTicketUpdate,
            ticketsReceivingState = ticketsReceivingState,
            drafts = drafts,
            ticketsForExecution = ticketsForExecution,
            ticketsPersonal = ticketsPersonal,
            tickets = tickets,
            deleteDraft = ticketsListViewModel::deleteDraft,
            deleteTicket = ticketsListViewModel::deleteTicket
        )
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        authParams: AuthParams? = remember { AuthParams() },
        isSearchEnabled: MutableState<Boolean> = remember { mutableStateOf(false) },
        searchText: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
        pagerState: PagerState = rememberPagerState(),
        fetchTickets: (url: String?, userId: Long) -> Unit = { _, _ -> },
        navigateToTicketFilter: () -> Unit = {},
        navigateToTicketCreate: () -> Unit = {},
        navigateToTicketUpdate: (TicketEntity) -> Unit = { _ -> },
        ticketsReceivingState: MutableState<NetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT),
        drafts: MutableState<List<TicketEntity>?> = mutableStateOf(listOf()),
        ticketsForExecution: MutableState<List<TicketEntity>?> = mutableStateOf(null),
        ticketsPersonal: MutableState<List<TicketEntity>?> = mutableStateOf(null),
        tickets: MutableState<List<TicketEntity>?> = mutableStateOf(null),
        deleteDraft: (TicketEntity) -> Unit = {},
        deleteTicket: (TicketEntity) -> Unit = {}
    ) {
        // TICKETS LOADING
        if ((ticketsReceivingState.value == NetworkState.LOADING || ticketsReceivingState.value == NetworkState.WAIT_FOR_INIT)
            && Constants.APPLICATION_MODE != ApplicationModes.OFFLINE
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

            authParams?.connectionParams?.url?.let {
                // Online mode
                CustomTabRow(
                    pagerState = pagerState,
                    values = MainMenuTabEnum.values(),
                    valueTitle = { it.title }
                )

                HorizontalPager(
                    count = MainMenuTabEnum.values().size,
                    state = pagerState,
                ) { pageNumber ->
                    when (pageNumber) {
                        MainMenuTabEnum.USER_AUTHOR_TICKETS.id -> {
                            MenuTicketList(
                                authParams = authParams,
                                tickets = ticketsPersonal,
                                onClickUpdate = { itTicket -> navigateToTicketUpdate(itTicket) },
                                emptyListTitle = "Заявок не найдено :("
                            )
                        }
                        MainMenuTabEnum.USER_EXECUTOR_TICKETS.id -> {
                            MenuTicketList(
                                authParams = authParams,
                                tickets = ticketsForExecution,
                                onClickUpdate = { itTicket -> navigateToTicketUpdate(itTicket) },
                                emptyListTitle = "Заявок не найдено :("
                            )
                        }
                        MainMenuTabEnum.DRAFTS.id -> {
                            MenuTicketList(
                                authParams = authParams,
                                tickets = drafts,
                                onClickUpdate = { itTicket -> navigateToTicketUpdate(itTicket) },
                                emptyListTitle = "Черновиков не найдено :(",
                                showTrashCan = true,
                                onTrashClick = { deleteDraft(it) }
                            )
                        }
                    }
                }
            } ?: run {
                // Offline mode
                val scrollCoroutineScope = rememberCoroutineScope()
                TabRow(
                    modifier = Modifier.height(40.dp),
                    selectedTabIndex = pagerState.currentPage,
                    contentColor = MaterialTheme.colors.onBackground,
                    tabs = {
                        MainMenuOfflineTabEnum.values().forEachIndexed { index, it ->
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
                                }
                            )
                        }
                    }
                )

                HorizontalPager(
                    count = MainMenuTabEnum.values().size,
                    state = pagerState,
                ) { pageNumber ->
                    when (pageNumber) {
                        MainMenuOfflineTabEnum.TICKETS.id -> {
                            MenuTicketList(
                                authParams = authParams,
                                tickets = tickets,
                                onClickUpdate = { itTicket -> navigateToTicketUpdate(itTicket) },
                                emptyListTitle = "Заявок не найдено :(",
                                showTrashCan = true,
                                onTrashClick = { deleteTicket(it) }
                            )
                        }
                        MainMenuOfflineTabEnum.DRAFTS.id -> {
                            MenuTicketList(
                                authParams = authParams,
                                tickets = drafts,
                                onClickUpdate = { itTicket -> navigateToTicketUpdate(itTicket) },
                                emptyListTitle = "Черновиков не найдено :(",
                                showTrashCan = true,
                                onTrashClick = { deleteDraft(it) }
                            )
                        }
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