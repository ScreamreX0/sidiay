package com.example.home.ui.ticket_update

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.BottomBarNav
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.ApplicationModes
import com.example.core.utils.ConstAndVars
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketStatuses
import com.example.domain.enums.states.LoadingState
import com.example.domain.enums.states.TicketOperationState
import com.example.home.ui.common.ITicketField
import com.example.home.ui.common.components.TicketUpdateBottomBar
import com.example.home.ui.common.components.TicketUpdateTopBar
import com.example.home.ui.common.impl.chip_rows.BrigadeChipRow
import com.example.home.ui.common.impl.chip_rows.EquipmentChipRow
import com.example.home.ui.common.impl.chip_rows.FacilitiesChipRow
import com.example.home.ui.common.impl.chip_rows.TransportChipRow
import com.example.home.ui.common.impl.clickable_texts.ExecutorClickableText
import com.example.home.ui.common.impl.clickable_texts.KindClickableText
import com.example.home.ui.common.impl.clickable_texts.PriorityClickableText
import com.example.home.ui.common.impl.clickable_texts.ServiceClickableText
import com.example.home.ui.common.impl.date_pickers.ClosingDatePicker
import com.example.home.ui.common.impl.date_pickers.PlaneDatePicker
import com.example.home.ui.common.impl.drop_down_menu.StatusDropDownMenu
import com.example.home.ui.common.impl.other.AuthorNonSelectableText
import com.example.home.ui.common.impl.other.CreationDateNonSelectableText
import com.example.home.ui.common.impl.other.IdNonSelectableText
import com.example.home.ui.common.impl.text_fields.CompletedWorkTextField
import com.example.home.ui.common.impl.text_fields.DescriptionTextField
import com.example.home.ui.common.impl.text_fields.ImprovementReasonTextField
import com.example.home.ui.common.impl.text_fields.NameTextField


class TicketUpdate {
    @Composable
    fun TicketUpdateScreen(
        navController: NavHostController,
        authParams: AuthParams = remember { AuthParams(user = UserEntity(id = 1)) },
        ticketUpdateViewModel: TicketUpdateViewModel = hiltViewModel(),
        ticket: TicketEntity = remember { TicketEntity(id = 1, author = UserEntity(id = 1), executor = UserEntity(id = 2), status = TicketStatuses.NEW) },
    ) {
        LaunchedEffect(key1 = null) {
            ticketUpdateViewModel.initFields(url = authParams.connectionParams?.url)
        }

        Content(
            navController = navController,
            authParams = authParams,
            ticket = remember { mutableStateOf(ticket) },
            ticketData = ticketUpdateViewModel.fields,
            fieldsLoadingState = ticketUpdateViewModel.fieldsLoadingState,
            updateTicketFunction = ticketUpdateViewModel::update,
            updatingResult = ticketUpdateViewModel.updatingResult,
            updateRestrictions = ticketUpdateViewModel::initRestrictions,
            restrictions = ticketUpdateViewModel.restrictions
        )
    }

    @Composable
    private fun Content(
        navController: NavHostController = rememberNavController(),
        authParams: AuthParams = remember { AuthParams() },
        ticket: MutableState<TicketEntity> = remember { mutableStateOf(TicketEntity()) },
        ticketData: MutableState<TicketData?> = remember { mutableStateOf(TicketData()) },
        fieldsLoadingState: MutableState<LoadingState> = remember { mutableStateOf(LoadingState.DONE) },
        updateTicketFunction: (String?, TicketEntity, AuthParams) -> Unit = { _, _, _ -> },
        updatingResult: MutableState<TicketOperationState> = remember { mutableStateOf(TicketOperationState.WAITING) },
        bottomBarSelectable: MutableState<Boolean> = remember { mutableStateOf(true) },
        updateRestrictions: (selectedTicketStatus: TicketStatuses, ticket: TicketEntity, currentUser: UserEntity?) -> Unit = { _, _, _ ->  },
        restrictions: MutableState<TicketRestriction> = remember { mutableStateOf(TicketRestriction.getEmpty()) }
    ) {
        val context = LocalContext.current
        val selectedTicketStatus = remember { mutableStateOf(ticket.value.status ?: TicketStatuses.NOT_FORMED) }

        LaunchedEffect(key1 = null) {
            updateRestrictions(
                selectedTicketStatus.value,
                ticket.value,
                authParams.user
            )
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
                navController = navController,
                iconsVisible = isTopIconsVisible,
                ticket = ticket
            )

            //
            // LOADING
            //
            if ((fieldsLoadingState.value == LoadingState.LOADING
                        || fieldsLoadingState.value == LoadingState.WAIT_FOR_INIT)
                && ConstAndVars.APPLICATION_MODE != ApplicationModes.DEBUG_AND_OFFLINE
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.layoutId("centralMiddleRef"),
                    color = MaterialTheme.colors.primary
                )
                return@ConstraintLayout
            }

            // Load error
            if (fieldsLoadingState.value == LoadingState.CONNECTION_ERROR) {
                androidx.compose.material.Text(
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

            //
            // Saving
            //
            when (updatingResult.value) {
                TicketOperationState.IN_PROCESS -> bottomBarSelectable.value = false
                TicketOperationState.DONE -> {
                    navController.popBackStack(
                        route = BottomBarNav.Home.route,
                        inclusive = false
                    )
                    Helper.showShortToast(context = context, text = "Заявка успешно сохранена")
                }

                TicketOperationState.OPERATION_ERROR -> {
                    Helper.showShortToast(context = context, text = "Ошибка сохранения")
                    bottomBarSelectable.value = true
                }

                TicketOperationState.CONNECTION_ERROR -> {
                    Helper.showShortToast(context = context, text = "Ошибка подключения")
                    bottomBarSelectable.value = true
                }

                else -> {}
            }

            // SET ICONS ON TOP BAR VISIBLE
            isTopIconsVisible.value = true

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

                IdNonSelectableText(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { false }).Content()
                StatusDropDownMenu(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.status == null },
                    selectedTicketStatus = selectedTicketStatus,
                    updateRestrictions = {
                        updateRestrictions(
                            selectedTicketStatus.value,
                            ticket.value,
                            authParams.user
                        )
                    },
                ).Content()
                PriorityClickableText(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    ticketData = ticketData,
                    isValueNull = remember { ticket.value.priority == null }).Content()
                ServiceClickableText(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    ticketData = ticketData,
                    isValueNull = remember { ticket.value.service == null }).Content()
                KindClickableText(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    ticketData = ticketData,
                    isValueNull = remember { ticket.value.kind == null }).Content()

                AuthorNonSelectableText(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.author == null }).Content()
                ExecutorClickableText(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    ticketData = ticketData,
                    isValueNull = remember { ticket.value.executor == null }).Content()
                BrigadeChipRow(
                    ticket = ticket,
                    ticketData = ticketData,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.brigade == null }).Content()
                TransportChipRow(
                    ticket = ticket,
                    ticketData = ticketData,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.transport == null }).Content()
                FacilitiesChipRow(
                    ticket = ticket,
                    ticketData = ticketData,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.facilities == null }).Content()
                EquipmentChipRow(
                    ticket = ticket,
                    ticketData = ticketData,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.equipment == null }).Content()
                ImprovementReasonTextField(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.improvement_reason == null }).Content()
                PlaneDatePicker(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.plane_date == null }).Content()

                ClosingDatePicker(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.closing_date == null }).Content()
                CreationDateNonSelectableText(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.creation_date == null }).Content()
                CompletedWorkTextField(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.completed_work == null }).Content()
                DescriptionTextField(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.description == null }).Content()
                NameTextField(
                    ticket = ticket,
                    ticketRestrictions = restrictions.value,
                    isValueNull = remember { ticket.value.name == null }).Content()
            }

            //
            // BOTTOM BAR
            //
            TicketUpdateBottomBar(
                modifier = Modifier.layoutId("bottomAppBarRef"),
                ticket = ticket,
                authParams = authParams,
                updateTicketFunction = updateTicketFunction,
                bottomBarSelectable = bottomBarSelectable
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
        AppTheme(isSystemInDarkTheme()) {
            Content()
        }
    }
}