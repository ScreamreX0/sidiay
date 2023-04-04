package com.example.home.ui.ticket_create

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme
import com.example.domain.data_classes.entities.DraftEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketCreateParams
import com.example.domain.enums.states.LoadingState
import com.example.home.ui.ticket_create.components.*


class TicketCreate {
    @Composable
    fun TicketCreateScreen(
        navController: NavHostController,
        authParams: AuthParams = remember { AuthParams() },
        ticketCreateViewModel: TicketCreateViewModel = hiltViewModel(),
        draft: DraftEntity = remember { DraftEntity() },
    ) {
        if (authParams.onlineMode) {
            ticketCreateViewModel.initFields()
        } else {
            ticketCreateViewModel.offlineInitFields()
        }

        Content(
            navController = navController,
            authParams = authParams,
            draft = draft,
            fields = ticketCreateViewModel.fields,
            fieldsLoadingState = ticketCreateViewModel.fieldsLoadingState,
        )
    }

    @Composable
    private fun Content(
        navController: NavHostController = rememberNavController(),
        authParams: AuthParams = remember { AuthParams() },
        draft: DraftEntity = remember { DraftEntity() },
        fields: MutableState<TicketCreateParams?> = remember { mutableStateOf(TicketCreateParams()) },
        fieldsLoadingState: MutableState<LoadingState> = remember { mutableStateOf(LoadingState.DONE) }
    ) {
        val mainScrollableState = rememberScrollState()

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
            CustomTopBar(
                modifier = Modifier.layoutId("topAppBarRef"),
                navController = navController,
                iconsVisible = isTopIconsVisible
            )

            //
            // LOADING
            //
            if (fieldsLoadingState.value == LoadingState.LOADING
                || fieldsLoadingState.value == LoadingState.WAIT_FOR_INIT
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.layoutId("centralMiddleRef"),
                    color = MaterialTheme.colors.primary
                )
                return@ConstraintLayout
            }

            // Load error
            if (fieldsLoadingState.value == LoadingState.ERROR) {
                Text(
                    modifier = Modifier.layoutId("centralMiddleRef"),
                    color = MaterialTheme.colors.primary,
                    text = if (authParams.onlineMode) {
                        "Нет подключения к интернету.\nВойдите в автономный режим"
                    } else {
                        "Нет данных для автономного режима.\nНужно хотя-бы раз войти в онлайн режим"
                    }
                )
                return@ConstraintLayout
            }

            // SET ICONS ON TOP BAR VISIBLE
            isTopIconsVisible.value = true

            //
            // TICKET CREATE ITEMS
            //
            Column(
                modifier = Modifier
                    .layoutId("ticketCreateRef")
                    .background(MaterialTheme.colors.background.copy(alpha = 0.98F))
                    .verticalScroll(mainScrollableState),
            ) {
                // Required fields title
                RequiredFieldsTitleComponent()

                // Facilities
                FacilitiesComponent(draft = draft, fields = fields)

                // Services
                ServicesComponent(draft = draft, fields = fields)

                // Kind
                KindComponent(draft = draft, fields = fields)

                // Priority
                PriorityComponent(draft = draft, fields = fields)

                // Optional fields title
                OptionalFieldsTitleComponent()

                // Name
                NameComponent(draft = draft)

                // Executor
                ExecutorComponent(draft = draft, fields = fields)

                // Brigade
                BrigadeComponent(draft = draft, fields = fields)

                // PlaneDate
                PlaneDateComponent(draft = draft)

                // Description
                DescriptionComponent(draft = draft)

                // Automatic fields title
                AutomaticFieldsTitleComponent()

                // Status
                StatusComponent(draft = draft)

                // Author
                AuthorComponent(authParams = authParams)
            }

            //
            // BOTTOM BAR
            //
            CustomBottomBar(modifier = Modifier.layoutId("bottomAppBarRef"))
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
    @Preview(heightDp = 1500)
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) {
            Content()
        }
    }
}
