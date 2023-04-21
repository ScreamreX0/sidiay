package com.example.home.ui.ticket_create

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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.core.utils.ConstAndVars
import com.example.core.utils.ApplicationModes
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.states.LoadingState
import com.example.domain.enums.states.TicketOperationState
import com.example.domain.enums.states.TicketOperationState.*
import com.example.home.ui.common.*
import com.example.home.ui.common.components.TicketCreateBottomBar
import com.example.home.ui.common.components.TicketCreateTopBar


class TicketCreate {

    @Composable
    fun TicketCreateScreen(
        navController: NavHostController,
        authParams: AuthParams = remember { AuthParams() },
        ticketCreateViewModel: TicketCreateViewModel = hiltViewModel(),
        draft: TicketEntity = rememberSaveable { TicketEntity() },
    ) {
        LaunchedEffect(key1 = null) {
            ticketCreateViewModel.initFields(url = authParams.connectionParams?.url)
        }

        Content(
            navController = navController,
            authParams = authParams,
            draft = remember { mutableStateOf(draft) },
            ticketData = ticketCreateViewModel.fields,
            fieldsLoadingState = ticketCreateViewModel.fieldsLoadingState,
            saveTicketFunction = ticketCreateViewModel::save,
            savingResult = ticketCreateViewModel.savingResult
        )
    }

    @Composable
    private fun Content(
        navController: NavHostController = rememberNavController(),
        authParams: AuthParams = remember { AuthParams() },
        draft: MutableState<TicketEntity> = remember { mutableStateOf(TicketEntity()) },
        ticketData: MutableState<TicketData?> = remember { mutableStateOf(TicketData()) },
        fieldsLoadingState: MutableState<LoadingState> = remember { mutableStateOf(LoadingState.DONE) },
        saveTicketFunction: (String?, TicketEntity) -> Unit = { _, _ -> },
        savingResult: MutableState<TicketOperationState> = remember { mutableStateOf(WAITING) },
        bottomBarSelectable: MutableState<Boolean> = remember { mutableStateOf(true) }
    ) {
        val context = LocalContext.current

        draft.value.author = authParams.user

        //
        // Saving
        //
        when (savingResult.value) {
            IN_PROCESS -> bottomBarSelectable.value = false
            DONE -> {
                navController.popBackStack(
                    route = BottomBarNav.Home.route,
                    inclusive = false
                )
                Helper.showShortToast(context = context, text = "Заявка успешно сохранена")
            }

            OPERATION_ERROR -> {
                Helper.showShortToast(context = context, text = "Ошибка сохранения")
                bottomBarSelectable.value = true
            }

            CONNECTION_ERROR -> {
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
            TicketCreateTopBar(
                modifier = Modifier.layoutId("topAppBarRef"),
                navController = navController,
                iconsVisible = isTopIconsVisible,
                draft = draft
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
            } else {
                isTopIconsVisible.value = true
            }

            // Load error
            if (fieldsLoadingState.value == LoadingState.CONNECTION_ERROR) {
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

            //
            // TICKET CREATE ITEMS
            //
            val mainScrollableState = rememberScrollState()
            Column(
                modifier = Modifier
                    .layoutId("ticketCreateRef")
                    .background(MaterialTheme.colors.background.copy(alpha = 0.98F))
                    .verticalScroll(mainScrollableState),
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                NameComponent(
                    ticket = draft,
                    ticketFieldsParams = TicketFieldParams(starred = false, isClickable = true)
                )

                FacilitiesComponent(
                    ticketData = ticketData,
                    ticket = draft,
                    ticketFieldsParams = TicketFieldParams(starred = false, isClickable = true)
                )

                ServiceComponent(
                    ticket = draft,
                    ticketData = ticketData,
                    ticketFieldsParams = TicketFieldParams(starred = true, isClickable = true)
                )

                KindComponent(
                    ticket = draft,
                    ticketData = ticketData,
                    ticketFieldsParams = TicketFieldParams(starred = true, isClickable = true)
                )

                PlaneDateComponent(
                    ticket = draft,
                    ticketFieldsParams = TicketFieldParams(starred = true, isClickable = true)
                )

                PriorityComponent(
                    ticket = draft,
                    ticketData = ticketData,
                    ticketFieldsParams = TicketFieldParams(starred = true, isClickable = true)
                )

                ExecutorComponent(
                    ticket = draft,
                    ticketData = ticketData,
                    ticketFieldsParams = TicketFieldParams(starred = true, isClickable = true)
                )
            }

            //
            // BOTTOM BAR
            //
            TicketCreateBottomBar(
                modifier = Modifier.layoutId("bottomAppBarRef"),
                draft = draft,
                authParams = authParams,
                saveTicketFunction = saveTicketFunction,
                bottomBarSelectable = bottomBarSelectable
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
        AppTheme(isSystemInDarkTheme()) {
            Content()
        }
    }
}