package com.example.home.ui.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.Graphs
import com.example.core.navigation.Screens
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.theme.DefaultTextStyle
import com.example.core.utils.*
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.enums.MainMenuTabEnum
import com.example.domain.enums.MainMenuTopAppBarEnum
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.enums.states.LoadingState
import com.example.home.ui.drafts.DraftsComponent
import com.example.home.ui.home.components.MenuTicketList
import com.example.home.ui.home.components.MenuSearch
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import kotlinx.coroutines.launch


class Home {
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun HomeScreen(
        homeViewModel: HomeViewModel = hiltViewModel(),
        navController: NavHostController = rememberNavController(),
        paddingValues: PaddingValues = remember { PaddingValues() },
        authParams: AuthParams? = remember { AuthParams() },
    ) {
        val applicationReceivingErrors = homeViewModel.applicationReceivingErrors
        val tickets = homeViewModel.tickets
        val drafts = homeViewModel.drafts

        LaunchedEffect(key1 = null) {
            homeViewModel.getTickets(
                url = authParams?.connectionParams?.url,
                userId = authParams?.user?.id ?: 0
            )
        }

        //
        // LOADING
        //
        if ((homeViewModel.ticketsLoadingState.value == LoadingState.LOADING
                    || homeViewModel.ticketsLoadingState.value == LoadingState.WAIT_FOR_INIT)
            && ConstAndVars.APPLICATION_MODE != ApplicationModes.DEBUG_AND_OFFLINE
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
            return
        }
        Content(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 50.dp
            ),
            navController = navController,
            authParams = authParams,
            tickets = tickets,
            drafts = drafts,
            applicationReceivingErrors = applicationReceivingErrors,
            getTickets = homeViewModel::getTickets,
        )
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        authParams: AuthParams? = remember { AuthParams() },
        tickets: MutableState<List<TicketEntity>> = rememberSaveable { mutableStateOf(listOf()) },
        drafts: MutableState<List<TicketEntity>> = rememberSaveable { mutableStateOf(listOf()) },
        isSearchEnabled: MutableState<Boolean> = remember { mutableStateOf(false) },
        searchText: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
        applicationReceivingErrors: MutableState<String?> = remember { mutableStateOf("") },
        pagerState: PagerState = rememberPagerState(),
        context: Context = LocalContext.current,
        getTickets: (url: String?, userId: Long) -> Unit = { _, _ -> },
    ) {
        // Observers
        applicationReceivingErrors.value?.let {
            Helper.showShortToast(context = context, text = it)
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
                        modifier = Modifier.clickable { navController.navigate(Screens.Home.TICKET_FILTER) },
                        painter = painterResource(id = MainMenuTopAppBarEnum.FILTER.icon),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier.clickable { navController.navigate(Screens.Home.TICKET_CREATE) },
                        painter = painterResource(id = MainMenuTopAppBarEnum.CREATE.icon),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier.clickable { getTickets(
                            authParams?.connectionParams?.url,
                            authParams?.user?.id ?: 0
                        ) },
                        painter = painterResource(id = MainMenuTopAppBarEnum.REFRESH.icon),
                        contentDescription = null
                    )
                }
            }

            // Tabs
            val scrollCoroutineScope = rememberCoroutineScope()
            TabRow(
                modifier = Modifier
                    .height(40.dp),
                selectedTabIndex = pagerState.currentPage,
            ) {
                MainMenuTabEnum.values().forEachIndexed { index, it ->
                    Tab(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background),
                        selected = pagerState.currentPage == index,
                        onClick = { scrollCoroutineScope.launch { pagerState.scrollToPage(index) } }) {
                        DefaultTextStyle {
                            Text(
                                text = it.title,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                }
            }

            // Pages
            HorizontalPager(
                count = MainMenuTabEnum.values().size,
                state = pagerState,
            ) {
                if (it == 0) {
                    MenuTicketList(
                        authParams = authParams,
                        tickets = tickets,
                        onClickUpdate = { itTicket ->
                            navController.navigate("${Screens.Home.TICKET_UPDATE}/${Helper.objToJson(itTicket)}")
                        }
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

    @OptIn(ExperimentalPagerApi::class)
    @ScreenPreview
    @Composable
    private fun TicketsListPreview() {
        AppTheme { Content() }
    }
}