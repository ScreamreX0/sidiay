package com.example.home.ui.ticket_create

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.Constants
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses
import com.example.domain.enums.states.NetworkState
import com.example.domain.enums.states.TicketOperationState.*
import com.example.home.ui.common.*
import com.example.home.ui.common.components.TicketCreateBottomBar
import com.example.home.ui.common.components.TicketCreateTopBar


class TicketCreate {
    @Composable
    fun TicketCreateScreen(
        authParams: AuthParams = AuthParams(),
        ticket: TicketEntity = TicketEntity(),
        navigateToBackWithMessage: (Context) -> Unit = { _ -> },
        navigateToBack: () -> Unit = {},
        ticketCreateViewModel: TicketCreateViewModel = hiltViewModel(),
    ) {
        LaunchedEffect(key1 = null) {
            ticketCreateViewModel.fetchTicketData(url = authParams.connectionParams?.url)
        }

        val context: Context = LocalContext.current
        val newTicket: MutableState<TicketEntity> = remember { mutableStateOf(ticket) }
        newTicket.value.author = authParams.user
        val bottomBarSelectable: MutableState<Boolean> = remember { mutableStateOf(true) }

        // Saving
        when (ticketCreateViewModel.savingTicketResult.value) {
            IN_PROCESS -> bottomBarSelectable.value = false
            DONE -> { navigateToBackWithMessage(context) }
            ERROR -> {
                Helper.showShortToast(context = context, text = ERROR.name)
                bottomBarSelectable.value = true
            }
            NetworkState.NO_SERVER_CONNECTION -> {
                Helper.showShortToast(context = context, text = NetworkState.NO_SERVER_CONNECTION.name)
                bottomBarSelectable.value = true
            }
            else -> {}
        }
        when (ticketCreateViewModel.savingDraftResult.value) {
            IN_PROCESS -> bottomBarSelectable.value = false
            DONE -> { navigateToBackWithMessage(context) }
            ERROR -> {
                Helper.showShortToast(context = context, text = ERROR.name)
                bottomBarSelectable.value = true
            }
            else -> {}
        }

        Content(
            authParams = authParams,
            ticket = newTicket,
            ticketData = ticketCreateViewModel.fields,
            fieldsLoadingState = ticketCreateViewModel.fieldsLoadingState,
            saveTicket = ticketCreateViewModel::save,
            bottomBarSelectable = bottomBarSelectable,
            getRestrictionsFunction = ticketCreateViewModel::getRestrictions,
            navigateToBack = navigateToBack,
            saveDraft = ticketCreateViewModel::saveDraft
        )
    }

    @Composable
    private fun Content(
        authParams: AuthParams = AuthParams(),
        ticket: MutableState<TicketEntity> = mutableStateOf(TicketEntity()),
        ticketData: MutableState<TicketData?> = mutableStateOf(TicketData()),
        fieldsLoadingState: MutableState<NetworkState> = mutableStateOf(NetworkState.DONE),
        saveTicket: (String?, TicketEntity) -> Unit = { _, _ -> },
        saveDraft: (TicketEntity) -> Unit = { _ -> },
        bottomBarSelectable: MutableState<Boolean> = mutableStateOf(true),
        getRestrictionsFunction: () -> TicketRestriction = { TicketRestriction.getEmpty() },
        navigateToBack: () -> Unit = {},
        isTopIconsVisible: MutableState<Boolean> = remember { mutableStateOf(false) }
    ) {
        ConstraintLayout(
            constraintSet = getConstraints(),
            modifier = Modifier.fillMaxSize(),
        ) {
            // TOP BAR
            TicketCreateTopBar(
                modifier = Modifier.layoutId("topAppBarRef"),
                navigateToBack = navigateToBack,
                iconsVisible = isTopIconsVisible,
                ticket = ticket
            )

            // LOADING
            if ((fieldsLoadingState.value == NetworkState.LOADING
                        || fieldsLoadingState.value == NetworkState.WAIT_FOR_INIT)
                && Constants.APPLICATION_MODE != ApplicationModes.OFFLINE
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.layoutId("centralMiddleRef"),
                    color = MaterialTheme.colors.primary
                )
                return@ConstraintLayout
            } else {
                isTopIconsVisible.value = true
            }

            // Load error
            if (fieldsLoadingState.value == NetworkState.NO_SERVER_CONNECTION) {
                Text(
                    modifier = Modifier.layoutId("centralMiddleRef"),
                    color = MaterialTheme.colors.primary,
                    text = if (authParams.connectionParams != null) {
                        "Нет подключения к интернету.\nВойдите в автономный режим"
                    } else {
                        "Нет данных для автономного режима.\nНужно хотя-бы раз войти в онлайн режим"
                    }
                )
                return@ConstraintLayout
            }

            // TICKET CREATE ITEMS
            val mainScrollableState = rememberScrollState()
            Column(
                modifier = Modifier
                    .layoutId("ticketCreateRef")
                    .background(MaterialTheme.colors.background)
                    .verticalScroll(mainScrollableState),
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                val restrictions = remember { mutableStateOf(getRestrictionsFunction()) }

                val fieldsFactory = TicketFieldsFactory(
                    ticketData = ticketData.value,
                    ticket = ticket,
                    ticketRestriction = restrictions,
                    updateRestrictions = { },
                    selectedTicketStatus = remember { mutableStateOf(TicketStatuses.NEW) },
                )

                fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.NAME, field = ticket.value.name)
                fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.FACILITIES, field = ticket.value.facilities)
                fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.SERVICE, field = ticket.value.service)
                fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.KIND, field = ticket.value.kind)
                fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.PLANE_DATE, field = ticket.value.plane_date)
                fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.PRIORITY, field = ticket.value.priority)
                fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.EXECUTOR, field = ticket.value.executor)
            }

            // BOTTOM BAR
            TicketCreateBottomBar(
                modifier = Modifier.layoutId("bottomAppBarRef"),
                bottomBarSelectable = bottomBarSelectable,
                saveTicket = { saveTicket(authParams.connectionParams?.url, ticket.value) },
                saveDraft = { saveDraft(ticket.value) }
            )
        }
    }

    private fun getConstraints() = ConstraintSet {
        val topAppBarRef = createRefFor("topAppBarRef")
        val ticketCreateRef = createRefFor("ticketCreateRef")
        val bottomAppBarRef = createRefFor("bottomAppBarRef")
        val centralMiddleRef = createRefFor("centralMiddleRef")

        constrain(topAppBarRef) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }
        constrain(bottomAppBarRef) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }
        constrain(ticketCreateRef) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(topAppBarRef.bottom)
            bottom.linkTo(bottomAppBarRef.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
        constrain(centralMiddleRef) {
            linkTo(parent.start, parent.end, bias = 0.5F)
            linkTo(parent.top, parent.bottom, bias = 0.5F)
        }
    }

    @Composable
    @Preview(heightDp = 1100)
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) { Content() }
    }
}