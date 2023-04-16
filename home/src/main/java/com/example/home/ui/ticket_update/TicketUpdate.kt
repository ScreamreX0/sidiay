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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
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
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.TicketStatuses
import com.example.domain.enums.states.LoadingState
import com.example.domain.enums.states.TicketOperationState
import com.example.home.ui.common.AuthorField
import com.example.home.ui.common.BrigadeComponent
import com.example.home.ui.common.DescriptionComponent
import com.example.home.ui.common.EquipmentComponent
import com.example.home.ui.common.ExecutorComponent
import com.example.home.ui.common.FacilitiesComponent
import com.example.home.ui.common.KindComponent
import com.example.home.ui.common.NameComponent
import com.example.home.ui.common.PlaneDateComponent
import com.example.home.ui.common.PriorityComponent
import com.example.home.ui.common.ServiceComponent
import com.example.home.ui.common.StatusComponent
import com.example.home.ui.common.TransportComponent
import com.example.home.ui.common.components.TicketUpdateTopBar


class TicketUpdate {
    @Composable
    fun TicketUpdateScreen(
        navController: NavHostController,
        authParams: AuthParams = remember { AuthParams() },
        ticketUpdateViewModel: TicketUpdateViewModel = hiltViewModel(),
        ticket: TicketEntity = remember { TicketEntity() },
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
            updatingResult = ticketUpdateViewModel.updatingResult

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
        bottomBarSelectable: MutableState<Boolean> = remember { mutableStateOf(true) }
    ) {
        val context = LocalContext.current
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

                NameComponent(
                    modifier = Modifier.disable(),
                    ticket = ticket,
                    isClickable = false
                )
                FacilitiesComponent(
                    modifier = Modifier.disable(),
                    ticketData = ticketData,
                    ticket = ticket,
                    isClickable = false
                )
                EquipmentComponent(
                    modifier = Modifier.disable(),
                    ticketData = ticketData,
                    ticket = ticket,
                    isClickable = false
                )
                TransportComponent(
                    modifier = Modifier.disable(),
                    ticketData = ticketData,
                    ticket = ticket,
                    isClickable = false
                )
                BrigadeComponent(
                    modifier = Modifier.disable(),
                    ticketData = ticketData,
                    ticket = ticket,
                    isClickable = false
                )
                NameComponent(
                    modifier = Modifier.disable(),
                    ticket = ticket,
                    isClickable = false
                )
                DescriptionComponent(
                    modifier = Modifier.disable(),
                    ticket = ticket,
                    isClickable = false
                )
                ServiceComponent(
                    modifier = Modifier.disable(),
                    ticket = ticket,
                    ticketData = ticketData,
                    isClickable = false
                )
                KindComponent(
                    modifier = Modifier.disable(),
                    ticket = ticket,
                    ticketData = ticketData,
                    isClickable = false
                )
                PriorityComponent(
                    modifier = Modifier.disable(),
                    ticket = ticket,
                    ticketData = ticketData,
                    isClickable = false
                )
                ExecutorComponent(
                    modifier = Modifier.disable(),
                    ticket = ticket,
                    ticketData = ticketData,
                    isClickable = false
                )
                PlaneDateComponent(
                    modifier = Modifier.disable(),
                    ticket = ticket,
                    isClickable = false
                )
                AuthorField(
                    modifier = Modifier.disable(),
                    authParams = authParams
                )
                StatusComponent(
                    modifier = Modifier.disable(),
                    statuses = listOf(TicketStatuses.ACCEPTED, TicketStatuses.CLOSED, TicketStatuses.COMPLETED),
                    isClickable = true,
                    ticket = ticket
                )
            }
//
//            //
//            // BOTTOM BAR
//            //
//            TicketCreateBottomBar(
//                modifier = Modifier.layoutId("bottomAppBarRef"),
//                draft = draft,
//                authParams = authParams,
//                saveTicketFunction = saveTicketFunction,
//                bottomBarSelectable = bottomBarSelectable
//            )
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

    private fun Modifier.disable(): Modifier = composed { alpha(0.7F) }

    @Composable
    @Preview(heightDp = 1100)
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) {
            Content()
        }
    }
}