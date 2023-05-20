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
import com.example.domain.data_classes.params.FilteringParams
import com.example.domain.data_classes.params.SortingParams
import com.example.domain.enums.JobTitlesEnum
import com.example.domain.enums.ui.MainMenuOfflineTabEnum
import com.example.domain.enums.ui.MainMenuTabEnum
import com.example.domain.enums.ui.MainMenuTopAppBarEnum
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.domain.enums.states.NetworkState
import com.example.home.ui.tickets_list.components.CustomScrollableTabRow
import com.example.home.ui.tickets_list.components.FilterDialog
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
        navigateToTicketCreate: () -> Unit,
        navigateToTicketUpdate: (TicketEntity) -> Unit,
    ) {
        val ticketsReceivingState = ticketsListViewModel.ticketsReceivingState
        val tickets = ticketsListViewModel.filteredAndSearchedTickets
        val errorMessage = ticketsListViewModel.errorMessage
        val drafts = ticketsListViewModel.drafts
        val ticketData = ticketsListViewModel.ticketData

        val context = LocalContext.current
        val sortingParams: MutableState<SortingParams> = remember { mutableStateOf(SortingParams()) }
        val filteringParams: MutableState<FilteringParams> = remember { mutableStateOf(FilteringParams()) }
        val searchText: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) }
        val isFilterDialogEnabled: MutableState<Boolean> = remember { mutableStateOf(false) }
        val fetchTickets = remember {
            {
                ticketsListViewModel.fetchTickets(
                    authParams?.connectionParams?.url,
                    authParams?.user?.id ?: 0,
                    filteringParams.value,
                    sortingParams.value,
                    searchText.value
                )
            }
        }
        // Fetching tickets
        LaunchedEffect(key1 = null) { fetchTickets() }
        // Fetching drafts
        LaunchedEffect(key1 = null) { ticketsListViewModel.fetchDrafts() }
        // Fetching ticket data
        LaunchedEffect(key1 = null) { ticketsListViewModel.fetchTicketData(authParams?.connectionParams?.url) }

        val filterTicketsCoroutineScope = rememberCoroutineScope()
        if (isFilterDialogEnabled.value) {
            FilterDialog(
                onConfirmButton = {
                    filterTicketsCoroutineScope.launch {
                        ticketsListViewModel.filterTickets(
                            filteringParams.value,
                            sortingParams.value,
                            searchText.value
                        )
                        isFilterDialogEnabled.value = false
                    }
                },
                sortingParams = sortingParams,
                filteringParams = filteringParams,
                isDialogOpened = isFilterDialogEnabled,
                ticketData = ticketData
            )
        }

        errorMessage.value?.let { Helper.showShortToast(context = context, text = it.toString()) }

        Content(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 50.dp
            ),
            authParams = authParams,
            fetchTickets = fetchTickets,
            navigateToTicketCreate = navigateToTicketCreate,
            navigateToTicketUpdate = navigateToTicketUpdate,
            ticketsReceivingState = ticketsReceivingState,
            drafts = drafts,
            tickets = tickets,
            deleteDraft = ticketsListViewModel::deleteDraft,
            searchText = searchText,
            isFilterDialogEnabled = isFilterDialogEnabled,
            searchTickets = ticketsListViewModel::searchTickets
        )
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        authParams: AuthParams? = AuthParams(),
        pagerState: PagerState = rememberPagerState(),

        // Searching and filtering
        isSearchEnabled: MutableState<Boolean> = mutableStateOf(false),
        isFilterDialogEnabled: MutableState<Boolean> = mutableStateOf(false),
        searchText: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
        searchTickets: suspend (searchText: TextFieldValue) -> Unit = {},

        // Tickets
        fetchTickets: () -> Unit = { },
        navigateToTicketCreate: () -> Unit = {},
        navigateToTicketUpdate: (TicketEntity) -> Unit = { _ -> },
        ticketsReceivingState: MutableState<NetworkState> = mutableStateOf(NetworkState.WAIT_FOR_INIT),
        tickets: MutableState<List<TicketEntity>?> = mutableStateOf(null),

        // Drafts
        drafts: MutableState<List<TicketEntity>?> = mutableStateOf(listOf()),
        deleteDraft: (TicketEntity) -> Unit = {},
    ) {
        // Tickets receiving progress
        if ((ticketsReceivingState.value == NetworkState.LOADING
                    || ticketsReceivingState.value == NetworkState.WAIT_FOR_INIT)
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
            TopAppBar(
                contentColor = MaterialTheme.colors.onBackground,
                backgroundColor = MaterialTheme.colors.background
            ) {
                if (isSearchEnabled.value) {
                    MenuSearch(
                        isSearchEnabled = isSearchEnabled,
                        textState = searchText,
                        searchTickets = searchTickets
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
                        modifier = Modifier.clickable { isFilterDialogEnabled.value = true },
                        painter = painterResource(id = MainMenuTopAppBarEnum.FILTER.icon),
                        contentDescription = null
                    )
                    if (authParams?.user?.employee?.jobTitle?.let { it == JobTitlesEnum.OPERATOR.value } != false) {
                        Icon(
                            modifier = Modifier.clickable { navigateToTicketCreate() },
                            painter = painterResource(id = MainMenuTopAppBarEnum.CREATE.icon),
                            contentDescription = null
                        )
                    }
                    Icon(
                        modifier = Modifier.clickable { fetchTickets() },
                        painter = painterResource(id = MainMenuTopAppBarEnum.REFRESH.icon),
                        contentDescription = null
                    )
                }
            }

            authParams?.connectionParams?.url?.let {
                // Online mode
                val scrollCoroutineScope = rememberCoroutineScope()
                TabRow(
                    modifier = Modifier.height(40.dp),
                    selectedTabIndex = pagerState.currentPage,
                    contentColor = MaterialTheme.colors.onBackground,
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
                        MainMenuTabEnum.TICKETS.id -> {
                            MenuTicketList(
                                authParams = authParams,
                                tickets = tickets.value,
                                onClickUpdate = { itTicket -> navigateToTicketUpdate(itTicket) },
                                emptyListTitle = "Заявок не найдено :("
                            )
                        }

                        MainMenuTabEnum.DRAFTS.id -> {
                            MenuTicketList(
                                authParams = authParams,
                                tickets = drafts.value,
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
                MenuTicketList(
                    authParams = authParams,
                    tickets = drafts.value,
                    onClickUpdate = { itTicket -> navigateToTicketUpdate(itTicket) },
                    emptyListTitle = "Черновиков не найдено :(",
                    showTrashCan = true,
                    onTrashClick = { deleteDraft(it) }
                )
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