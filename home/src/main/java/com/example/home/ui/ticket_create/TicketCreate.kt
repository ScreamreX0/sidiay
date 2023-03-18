package com.example.home.ui.ticket_create

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core.ui.theme.AppTheme
import com.example.domain.data_classes.entities.DraftEntity
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketCreateParams
import com.example.domain.enums.states.LoadingState
import com.example.home.ui.ticket_create.components.*
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import java.util.*


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
        val context = LocalContext.current
        val mainScrollableState = rememberScrollState()

        // Name
        val draftName = remember { mutableStateOf(draft.name) }

        // Status
        val draftStatus = remember { mutableStateOf(draft.draftStatus) }

        // Description
        val draftDescription = remember { mutableStateOf(draft.description) }

        // Executor
        val draftExecutor = remember { mutableStateOf(draft.executor) }
        val isExecutorsDialogOpened = remember { mutableStateOf(false) }
        val executorsScrollState = rememberScrollState()

        // Expiration date
        val draftExpirationDate = remember { mutableStateOf(draft.expiration_date) }

        // Kind
        val draftKind = remember { mutableStateOf(draft.kind) }
        val isKindDialogOpened = remember { mutableStateOf(false) }
        val kindsScrollState = rememberScrollState()

        // Priority
        val draftPriority = remember {
            mutableStateOf(
                draft.priority
            )
        }
        val isPriorityDialogOpened = remember { mutableStateOf(false) }
        val prioritiesScrollState = rememberScrollState()

        // Service
        val draftService = remember { mutableStateOf(draft.service) }
        val isServicesDialogOpened = remember { mutableStateOf(false) }
        val servicesScrollState = rememberScrollState()

        // Facilities
        val draftFacilities: MutableList<FacilityEntity?> = remember {
            draft.facilities?.toMutableStateList() ?: mutableStateListOf()
        }
        val isFacilitiesDialogOpened = remember { mutableStateOf(false) }
        val facilitiesScrollState = rememberScrollState()

        // Brigade
        val draftBrigade: MutableList<UserEntity?> = remember {
            draft.brigade?.toMutableStateList() ?: mutableStateListOf()
        }
        val isBrigadeDialogOpened = remember { mutableStateOf(false) }
        val brigadeScrollState = rememberScrollState()

        // Plane date
        val draftPlaneDate = remember { mutableStateOf(draft.plane_date) }
        val isPlaneDateOpened = remember { mutableStateOf(false) }

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
            TopAppBar().Content(
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

            //
            // SET ICONS ON TOP BAR VISIBLE
            //
            isTopIconsVisible.value = true

            //
            // DIALOGS
            //
            if (isFacilitiesDialogOpened.value) {  // Facilities
                SingleSelectionDialog().FacilitiesDialog(
                    scrollState = facilitiesScrollState,
                    isDialogOpened = isFacilitiesDialogOpened,
                    fields = fields,
                    draftFacilities = draftFacilities,
                )
            } else if (isBrigadeDialogOpened.value) {  // Brigade
                SingleSelectionDialog().BrigadeDialog(
                    scrollState = brigadeScrollState,
                    isDialogOpened = isBrigadeDialogOpened,
                    fields = fields,
                    draftBrigade = draftBrigade
                )
            } else if (isPlaneDateOpened.value) {  // Plane date
                DatePicker().DatePickerDialog(
                    date = draftPlaneDate,
                    isDialogOpened = isPlaneDateOpened,
                )
            } else if (isServicesDialogOpened.value) {  // Service
                SingleSelectionDialog().ServiceDialog(
                    scrollState = servicesScrollState,
                    isDialogOpened = isServicesDialogOpened,
                    fields = fields,
                    draftService = draftService
                )
            } else if (isKindDialogOpened.value) {  // Kind
                SingleSelectionDialog().KindDialog(
                    scrollState = kindsScrollState,
                    isDialogOpened = isKindDialogOpened,
                    fields = fields,
                    draftKinds = draftKind,
                )
            } else if (isPriorityDialogOpened.value) {  // Priority
                SingleSelectionDialog().PriorityDialog(
                    scrollState = prioritiesScrollState,
                    isDialogOpened = isPriorityDialogOpened,
                    fields = fields,
                    draftPriority = draftPriority,
                )
            } else if (isExecutorsDialogOpened.value) {  // Executor
                SingleSelectionDialog().ExecutorDialog(
                    scrollState = executorsScrollState,
                    isDialogOpened = isExecutorsDialogOpened,
                    fields = fields,
                    draftExecutor = draftExecutor,
                )
            }

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
                Title(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    text = "Обязательные поля",
                    fontSize = MaterialTheme.typography.h5.fontSize
                )

                // Facilities
                TicketCreateItem(
                    title = "Объекты",
                    icon = com.example.core.R.drawable.baseline_oil_barrel_24
                ) {
                    ChipRow().Facilities(
                        draftFacilities = draftFacilities,
                        isDialogOpened = isFacilitiesDialogOpened,
                    )
                }

                // Services
                TicketCreateItem(
                    title = "Сервисы",
                    icon = com.example.core.R.drawable.ic_baseline_miscellaneous_services_24
                ) {
                    SelectableText(
                        nullLabel = "Выбрать сервис",
                        label = draftService.value?.title
                    ) { isServicesDialogOpened.value = true }
                }

                // Kind
                TicketCreateItem(
                    title = "Вид",
                    icon = com.example.core.R.drawable.baseline_format_list_bulleted_24
                ) {
                    SelectableText(
                        label = draftKind.value?.title,
                        nullLabel = "Выбрать вид"
                    ) { isKindDialogOpened.value = true }
                }

                // Priority
                TicketCreateItem(
                    title = "Приоритет",
                    icon = com.example.core.R.drawable.baseline_priority_high_24
                ) {
                    SelectableText(
                        label = draftPriority.value?.title,
                        nullLabel = "Выбрать приоритет",
                    ) { isPriorityDialogOpened.value = true }
                }

                // Optional fields title
                Title(
                    text = "Необязательные поля",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    modifier = Modifier.padding(top = 20.dp)
                )

                // Name
                TicketCreateItem(
                    title = "Название",
                    icon = com.example.core.R.drawable.baseline_title_24
                ) {
                    CustomTextField(
                        text = draftName,
                        hint = "Ввести название"
                    )
                }

                // Executor
                TicketCreateItem(
                    title = "Исполнитель",
                    icon = com.example.core.R.drawable.ic_baseline_person_24
                ) {
                    SelectableText(
                        label = draftExecutor.value?.getFullName(),
                        nullLabel = "Выбрать исполнителя"
                    ) { isExecutorsDialogOpened.value = true }
                }

                // Brigade
                TicketCreateItem(
                    title = "Бригада",
                    icon = com.example.core.R.drawable.baseline_people_24
                ) {
                    ChipRow().Brigade(
                        draftBrigade = draftBrigade,
                        isDialogOpened = isBrigadeDialogOpened,
                    )
                }

                // PlaneDate
                TicketCreateItem(
                    title = "Плановая дата",
                    icon = com.example.core.R.drawable.ic_baseline_calendar_month_24
                ) {
                    SelectableText(
                        label = draftPlaneDate
                            .value?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        nullLabel = "Выбрать плановую дату"
                    ) { isPlaneDateOpened.value = true }
                }

                // Description
                TicketCreateItem(
                    title = "Описание",
                    icon = com.example.core.R.drawable.baseline_text_format_24
                ) {
                    CustomTextField(
                        text = draftDescription,
                        hint = "Ввести описание"
                    )
                }

                // Automatic fields title
                Title(
                    text = "Автоматические поля",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    modifier = Modifier.padding(top = 20.dp)
                )

                // Status
                TicketCreateItem(
                    title = "Статус",
                    icon = com.example.core.R.drawable.ic_baseline_playlist_add_check_24
                ) {
                    SelectableText(
                        label = draftStatus.value.title,
                        nullLabel = "Выбрать статус"
                    )
                }

                // Author
                TicketCreateItem(
                    title = "Автор",
                    icon = com.example.core.R.drawable.ic_baseline_person_24
                ) {
                    SelectableText(
                        label = authParams.user?.getFullName(),
                        nullLabel = "Автор не определен"
                    )
                }
            }

            //
            // BOTTOM BAR
            //
            BottomAppBar().Content(modifier = Modifier.layoutId("bottomAppBarRef"))
        }
    }

    @Composable
    internal fun TicketCreateItem(
        title: String,
        icon: Int,
        item: @Composable () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            Icon(
                modifier = Modifier
                    .height(45.dp)
                    .width(45.dp)
                    .padding(end = 10.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground
            )
            Column {
                // Subtitle
                Text(
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = MaterialTheme.typography.h3.fontSize
                )

                // Item
                item.invoke()
            }
        }
    }

    @Composable
    private fun CustomTextField(
        text: MutableState<String?>,
        hint: String
    ) {
        BasicTextField(
            value = text.value ?: "",
            onValueChange = { text.value = it },
            textStyle = TextStyle.Default.copy(
                fontSize = 24.sp,
                color = MaterialTheme.colors.onBackground,
            ),
            cursorBrush = SolidColor(MaterialTheme.colors.onBackground),
            decorationBox = { innerTextField ->
                if (text.value?.isEmpty() != false) {
                    Text(
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.7F),
                        text = text.value ?: hint,
                    )
                }
                innerTextField()
            }
        )
    }

    @Composable
    private fun Title(
        modifier: Modifier = Modifier,
        text: String,
        fontSize: TextUnit,
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp, start = 25.dp, end = 25.dp),
            text = text,
            fontSize = fontSize,
            overflow = TextOverflow.Visible,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.9F)
        )
    }

    @Composable
    private fun SelectableText(
        modifier: Modifier = Modifier,
        label: String?,
        nullLabel: String,
        onClick: () -> Unit = {},
    ) {
        if (label == null) {
            Text(
                modifier = modifier
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { onClick() },
                style = TextStyle(textDecoration = TextDecoration.Underline),
                text = nullLabel,
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.8F),
            )
        } else {
            Text(
                modifier = modifier
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { onClick() },
                text = label,
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = MaterialTheme.colors.onBackground,
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
    @Preview(heightDp = 1500)
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) {
            Content()
        }
    }
}
