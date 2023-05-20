package com.example.home.ui.ticket_update

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Constants
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses
import com.example.domain.enums.states.INetworkState
import com.example.domain.enums.states.NetworkState
import com.example.domain.enums.states.TicketOperationState
import com.example.home.ui.common.impl.TicketFieldsFactory
import com.example.home.ui.common.components.TicketUpdateBottomBar
import com.example.home.ui.common.components.TicketUpdateTopBar


class TicketUpdate {
    @Composable
    fun TicketUpdateScreen(
        authParams: AuthParams = remember { AuthParams(user = UserEntity(id = 1)) },
        ticketUpdateViewModel: TicketUpdateViewModel = hiltViewModel(),
        ticket: TicketEntity = remember {
            TicketEntity(
                id = 1,
                author = UserEntity(id = 1),
                executors = listOf(UserEntity(id = 2)),
                status = TicketStatuses.NEW.value
            )
        },
        navigateToBackWithMessage: (Context) -> Unit = { _ -> },
        navigateToBack: () -> Unit = {},
    ) {
        if (ticketUpdateViewModel.savingDraftResult.value == TicketOperationState.DONE) navigateToBackWithMessage(LocalContext.current)

        val selectedTicketStatus = remember { mutableStateOf(TicketStatuses.getByValue(ticket.status) ?: TicketStatuses.NOT_FORMED) }
        val mutableTicket = remember { mutableStateOf(ticket) }

        LaunchedEffect(key1 = null) {
            ticketUpdateViewModel.fetchTicketData(
                url = authParams.connectionParams?.url,
                ticket = mutableTicket.value,
                currentUser = authParams.user
            )
        }

        Content(
            authParams = authParams,
            ticket = mutableTicket,
            ticketData = ticketUpdateViewModel.ticketData,
            fieldsLoadingState = ticketUpdateViewModel.ticketDataLoadingState,
            updateTicketFunction = ticketUpdateViewModel::update,
            updatingResult = ticketUpdateViewModel.updatingResult,
            updateRestrictions = ticketUpdateViewModel::fetchRestrictions,
            restrictions = ticketUpdateViewModel.restrictions,
            updatingMessage = ticketUpdateViewModel.updatingMessage,
            navigateToBackWithMessage = navigateToBackWithMessage,
            navigateToBack = navigateToBack,
            selectedTicketStatus = selectedTicketStatus,
            saveDraft = ticketUpdateViewModel::saveDraft
        )
    }

    @Composable
    private fun Content(
        authParams: AuthParams = remember { AuthParams() },
        ticket: MutableState<TicketEntity> = remember { mutableStateOf(TicketEntity()) },
        ticketData: MutableState<TicketData?> = remember { mutableStateOf(TicketData()) },
        fieldsLoadingState: MutableState<INetworkState> = remember { mutableStateOf(NetworkState.DONE) },
        updateTicketFunction: (ticket: TicketEntity, authParams: AuthParams) -> Unit = { _, _ -> },
        updatingResult: MutableState<INetworkState> = remember { mutableStateOf(TicketOperationState.WAITING) },
        bottomBarSelectable: MutableState<Boolean> = remember { mutableStateOf(true) },
        updateRestrictions: (selectedTicketStatus: TicketStatuses, ticket: TicketEntity, currentUser: UserEntity?) -> Unit = { _, _, _ -> },
        restrictions: MutableState<TicketRestriction> = remember { mutableStateOf(TicketRestriction.getEmpty()) },
        updatingMessage: MutableState<String?> = mutableStateOf(null),
        navigateToBackWithMessage: (Context) -> Unit = { _ -> },
        navigateToBack: () -> Unit = {},
        context: Context = LocalContext.current,
        selectedTicketStatus: MutableState<TicketStatuses> = mutableStateOf(TicketStatuses.NOT_FORMED),
        saveDraft: (TicketEntity, UserEntity) -> Unit = { _, _ -> }
    ) {
        LaunchedEffect(key1 = null) {
            updateRestrictions(
                selectedTicketStatus.value,
                ticket.value,
                authParams.user
            )
        }

        //
        // Saving
        //
        when (updatingResult.value) {
            TicketOperationState.IN_PROCESS -> bottomBarSelectable.value = false
            TicketOperationState.DONE -> {
                navigateToBackWithMessage(context)
            }

            TicketOperationState.ERROR -> {
                updatingMessage.value?.let {
                    Helper.showShortToast(context = context, text = it)
                } ?: Helper.showShortToast(context = context, text = "Ошибка сохранения")
                bottomBarSelectable.value = true
            }

            NetworkState.NO_SERVER_CONNECTION -> {
                Helper.showShortToast(context = context, text = "Ошибка подключения")
                bottomBarSelectable.value = true
            }

            else -> {}
        }

        //
        // MAIN CONSTRAINT
        //
        ConstraintLayout(
            constraintSet = getConstraints(),
            modifier = Modifier.fillMaxSize(),
        ) {
            //
            // TOP BAR
            //
            val isTopIconsVisible = remember { mutableStateOf(false) }
            TicketUpdateTopBar(
                modifier = Modifier.layoutId("topAppBarRef"),
                navigateToBack = navigateToBack,
                ticket = ticket
            )

            //
            // LOADING
            //
            if ((fieldsLoadingState.value == NetworkState.LOADING
                        || fieldsLoadingState.value == NetworkState.WAIT_FOR_INIT)
                && Constants.APPLICATION_MODE != ApplicationModes.OFFLINE
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.layoutId("centralMiddleRef"),
                    color = MaterialTheme.colors.primary
                )
                return@ConstraintLayout
            }

            // Load error
            when (fieldsLoadingState.value) {
                NetworkState.ERROR -> {
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

                else -> isTopIconsVisible.value = true
            }

            //
            // TICKET UPDATE ITEMS
            //
            val mainScrollableState = rememberScrollState()
            Column(
                modifier = Modifier
                    .layoutId("ticketUpdateRef")
                    .background(MaterialTheme.colors.background.copy(alpha = 0.98F))
                    .verticalScroll(mainScrollableState),
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                val fieldsFactory = TicketFieldsFactory(
                    ticketData = ticketData.value,
                    ticket = ticket,
                    ticketRestriction = restrictions,
                    updateRestrictions = { updateRestrictions(selectedTicketStatus.value, ticket.value, authParams.user) },
                    selectedTicketStatus = selectedTicketStatus,
                )

                with(ticket.value) {
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.ID, field = id)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.STATUS, field = status)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.AUTHOR, field = author)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.FIELD, field = field)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.DISPATCHER, field = dispatcher)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.EXECUTORS_NOMINATOR, field = executors_nominator)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.QUALITY_CONTROLLERS_NOMINATOR, field = quality_controllers_nominator)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.CREATION_DATE, field = creation_date)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.TICKET_NAME, field = ticket_name)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.DESCRIPTION_OF_WORK, field = description_of_work)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.KIND, field = kind)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.SERVICE, field = service)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.FACILITIES, field = facilities)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.EQUIPMENT, field = equipment)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.TRANSPORT, field = transport)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.PRIORITY, field = priority)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.ASSESSED_VALUE, field = assessed_value)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.ASSESSED_VALUE_DESCRIPTION, field = assessed_value_description)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.REASON_FOR_CANCELLATION, field = reason_for_cancellation)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.REASON_FOR_REJECTION, field = reason_for_rejection)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.EXECUTION_PROBLEM_DESCRIPTION, field = execution_problem_description)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.EXECUTORS, field = executors)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.PLANE_DATE, field = plane_date)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.REASON_FOR_SUSPENSION, field = reason_for_suspension)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.COMPLETED_WORK, field = completed_work)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.QUALITY_CONTROLLERS, field = quality_controllers)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.IMPROVEMENT_COMMENT, field = improvement_comment)
                    fieldsFactory.GetField(fieldEnum = TicketFieldsEnum.CLOSING_DATE, field = closing_date)
                }
            }

            //
            // BOTTOM BAR
            //
            TicketUpdateBottomBar(
                modifier = Modifier.layoutId("bottomAppBarRef"),
                bottomBarSelectable = bottomBarSelectable,
                updateTicket = { updateTicketFunction(ticket.value.copy(status = selectedTicketStatus.value.value), authParams) },
                saveDraft = { saveDraft(ticket.value, authParams.user!!) }
            )
        }
    }

    private fun getConstraints() = ConstraintSet {
        val topAppBarRef = createRefFor("topAppBarRef")
        val ticketCreateRef = createRefFor("ticketUpdateRef")
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
    @Preview(heightDp = 1600)
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) { Content() }
    }
}