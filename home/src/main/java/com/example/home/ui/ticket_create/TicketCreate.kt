package com.example.home.ui.ticket_create

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme
import com.example.core.ui.utils.ScreenPreview
import com.example.domain.data_classes.entities.DraftEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketCreateParams
import com.example.domain.enums.states.LoadingState
import com.example.home.ui.ticket_create.components.BottomAppBar
import com.example.home.ui.ticket_create.components.Facilities
import com.example.home.ui.ticket_create.components.TopAppBar


class TicketCreate {
    @Composable
    fun TicketCreateScreen(
        navController: NavHostController,
        authParams: AuthParams = AuthParams(),
        ticketCreateViewModel: TicketCreateViewModel = hiltViewModel(),
        draft: MutableState<DraftEntity> = remember { mutableStateOf(DraftEntity()) },
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
        authParams: AuthParams = AuthParams(),
        draft: MutableState<DraftEntity> = remember { mutableStateOf(DraftEntity()) },
        fields: MutableState<TicketCreateParams?> = remember { mutableStateOf(TicketCreateParams()) },
        fieldsLoadingState: MutableState<LoadingState> = remember { mutableStateOf(LoadingState.DONE) }
    ) {
        ConstraintLayout(
            constraintSet = getConstraints(),
            modifier = Modifier.fillMaxSize(),
        ) {
            //
            // Top app bar
            //
            val iconsVisible = remember { mutableStateOf(false) }
            TopAppBar().Content(
                modifier = Modifier.layoutId("topAppBarRef"),
                navController = navController,
                iconsVisible = iconsVisible
            )

            if (fieldsLoadingState.value == LoadingState.LOADING
                || fieldsLoadingState.value == LoadingState.WAIT_FOR_INIT
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.layoutId("centralMiddleRef"),
                    color = MaterialTheme.colors.primary
                )
                return@ConstraintLayout
            }

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

            // Set icons on top app bar visible
            iconsVisible.value = true

            //
            // Middle section
            //
            val scrollableState = rememberScrollState()
            Column(
                modifier = Modifier
                    .layoutId("ticketCreateRef")
                    .background(MaterialTheme.colors.background)
                    .verticalScroll(scrollableState),
            ) {
                // Required fields
                Title(
                    modifier = Modifier.layoutId("requiredFieldsRef"),
                    text = "Обязательные поля",
                    fontSize = MaterialTheme.typography.h5.fontSize
                )

                // Objects
                val isDialogOpened = remember { mutableStateOf(false) }
                val facilitiesScrollState = rememberScrollState()
                val selectedFacilities = remember {
                    mutableStateListOf(
                        *(BooleanArray(fields.value?.facilities?.size ?: 0).toTypedArray())
                    )
                }

                if (isDialogOpened.value) {
                    Facilities().FacilitiesDialog(
                        facilitiesScrollState = facilitiesScrollState,
                        isDialogOpened = isDialogOpened,
                        fields = fields,
                        draft = draft,
                        selectedFacilities = selectedFacilities,
                    )
                }

                Facilities().Content(
                    modifier = Modifier.layoutId("objectsRef"),
                    draft = draft,
                    isDialogOpened = isDialogOpened,
                )

                // Services
                Text(
                    modifier = Modifier
                        .layoutId("serviceRef"),
                    text = "Сервисы",
                    fontSize = MaterialTheme.typography.h5.fontSize
                )

                // Kind
                // Priority

                // OptionalFields
                Title(
                    modifier = Modifier.layoutId("optionalFieldsRef"),
                    text = "Необязательные поля",
                    fontSize = MaterialTheme.typography.h5.fontSize
                )

                // Name
                TextField(
                    modifier = Modifier.layoutId("nameRef"),
                    value = draft.value.name ?: "",
                    label = { Text("[Название заявки]") },
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = MaterialTheme.colors.onPrimary,
                        disabledTextColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.6F),
                        backgroundColor = Color.Transparent,
                        focusedLabelColor = MaterialTheme.colors.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colors.onPrimary,
                        cursorColor = MaterialTheme.colors.onPrimary,
                        disabledIndicatorColor = MaterialTheme.colors.onPrimary
                    )
                )

                // Executor
                // Brigade
                // PlaneDate
                // ExpirationDate
                // Description

                // AutomaticFields
                Title(
                    modifier = Modifier.layoutId("automaticFieldsRef"),
                    text = "Автоматические поля",
                    fontSize = MaterialTheme.typography.h5.fontSize
                )

                // Status
                // Author
                // CreationDate

            }

            // Bottom app bar
            BottomAppBar().Content(modifier = Modifier.layoutId("bottomAppBarRef"))
        }
    }

    @Composable
    internal fun TicketCreateItem(
        title: String,
        icon: Int,
        item: @Composable () -> Unit
    ) {
        Row {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Column {
                // Subtitle
                Text(
                    text = title,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.8F),
                    fontSize = MaterialTheme.typography.h3.fontSize
                )

                // Item
                item.invoke()
            }
        }
    }

    @Composable
    private fun Title(
        modifier: Modifier,
        text: String,
        fontSize: TextUnit
    ) {
        Text(
            modifier = modifier,
            text = text,
            fontSize = fontSize,
            overflow = TextOverflow.Visible,
            fontWeight = FontWeight.Bold,
        )
    }

    private fun getConstraints() = ConstraintSet {
        val centralMiddleRef = createRefFor("centralMiddleRef")
        constrain(centralMiddleRef) {
            linkTo(parent.start, parent.end, bias = 0.5F)
            linkTo(parent.top, parent.bottom, bias = 0.5F)
        }

        //
        // Main sections
        //
        val topAppBarRef = createRefFor("topAppBarRef")
        val ticketCreateRef = createRefFor("ticketCreateRef")
        val bottomAppBarRef = createRefFor("bottomAppBarRef")
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

        //
        // Required fields
        //
        val requiredFieldsRef = createRefFor("requiredFieldsRef")
        val objectsRef = createRefFor("objectsRef")
        val serviceRef = createRefFor("serviceRef")
        val kindRef = createRefFor("kindRef")
        val priorityRef = createRefFor("priorityRef")

        //
        // Optional fields
        //
        val optionalFieldsRef = createRefFor("optionalFieldsRef")
        val nameRef = createRefFor("nameRef")
        val executorRef = createRefFor("executorRef")
        val brigadeRef = createRefFor("brigadeRef")
        val planeDateRef = createRefFor("planeDateRef")
        val expirationDateRef = createRefFor("expirationDateRef")
        val descriptionRef = createRefFor("descriptionRef")

        //
        // Automatic fields
        //
        val automaticFieldsRef = createRefFor("automaticFieldsRef")
        val statusRef = createRefFor("statusRef")
        val authorRef = createRefFor("authorRef")
        val creationDateRef = createRefFor("creationDateRef")
    }

    @Composable
    @ScreenPreview
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) {
            Content()
        }
    }
}
