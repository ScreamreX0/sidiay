package com.example.home.ui.ticket_create

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.example.domain.enums.TicketPriorityEnum
import com.example.domain.enums.states.LoadingState
import com.example.home.ui.ticket_create.components.BottomAppBar
import com.example.home.ui.ticket_create.components.ChipRow
import com.example.home.ui.ticket_create.components.SingleSelectionDialog
import com.example.home.ui.ticket_create.components.TopAppBar
import java.text.DateFormat
import java.util.Calendar


class TicketCreate {
    @Composable
    fun TicketCreateScreen(
        navController: NavHostController,
        authParams: AuthParams = AuthParams(),
        ticketCreateViewModel: TicketCreateViewModel = hiltViewModel(),
        draft: DraftEntity = DraftEntity(),
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
        draft: DraftEntity = DraftEntity(),
        fields: MutableState<TicketCreateParams?> = remember { mutableStateOf(TicketCreateParams()) },
        fieldsLoadingState: MutableState<LoadingState> = remember { mutableStateOf(LoadingState.DONE) }
    ) {
        val draftId = remember { mutableStateOf(draft.id) }
        val draftName = remember { mutableStateOf(draft.name) }
        val draftStatus = remember { mutableStateOf(draft.draftStatus) }
        val draftFacilities: MutableList<FacilityEntity?> = remember {
            draft.facilities?.toMutableStateList() ?: mutableStateListOf()
        }
        val draftDescription = remember { mutableStateOf(draft.description) }
        val draftExecutor = remember { mutableStateOf(draft.executor) }
        val draftBrigade: MutableList<UserEntity?> = remember {
            draft.brigade?.toMutableStateList() ?: mutableStateListOf()
        }
        val draftExpirationDate = remember { mutableStateOf(draft.expiration_date) }
        val draftKind = remember { mutableStateOf(draft.kind) }
        val draftPlaneDate = remember { mutableStateOf(draft.plane_date) }
        val draftPriority = remember { mutableStateOf(draft.priority) }
        val draftService = remember { mutableStateOf(draft.service) }

        // Dialog vars
        val isFacilitiesDialogOpened = remember { mutableStateOf(false) }
        val isServicesDialogOpened = remember { mutableStateOf(false) }
        val isKindDialogOpened = remember { mutableStateOf(false) }
        val isPriorityDialogOpened = remember { mutableStateOf(false) }
        val isBrigadeDialogOpened = remember { mutableStateOf(false) }

        ConstraintLayout(
            constraintSet = getConstraints(),
            modifier = Modifier.fillMaxSize(),
        ) {
            // Top app bar
            val isTopIconsVisible = remember { mutableStateOf(false) }
            TopAppBar().Content(
                modifier = Modifier.layoutId("topAppBarRef"),
                navController = navController,
                iconsVisible = isTopIconsVisible
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
            isTopIconsVisible.value = true

            //
            // Middle section
            //
            val scrollableState = rememberScrollState()
            Column(
                modifier = Modifier
                    .layoutId("ticketCreateRef")
                    .background(MaterialTheme.colors.background.copy(alpha = 0.98F))
                    .verticalScroll(scrollableState),
            ) {
                // Facilities dialog
                val facilitiesScrollState = rememberScrollState()
                val brigadeScrollState = rememberScrollState()
                if (isFacilitiesDialogOpened.value) {
                    SingleSelectionDialog().FacilitiesDialog(
                        facilitiesScrollState = facilitiesScrollState,
                        isDialogOpened = isFacilitiesDialogOpened,
                        fields = fields,
                        draftFacilities = draftFacilities,
                    )
                } else if (isBrigadeDialogOpened.value) {
                    SingleSelectionDialog().BrigadeDialog(
                        brigadeScrollState = brigadeScrollState,
                        isDialogOpened = isBrigadeDialogOpened,
                        fields = fields,
                        draftBrigade = draftBrigade
                    )
                }

                //
                // Required fields
                //
                Title(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    text = "Обязательные поля",
                    fontSize = MaterialTheme.typography.h5.fontSize
                )

                // Facilities list
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
                        label = draft.service ?: "Выбрать сервис"
                    ) {
                        TODO("Add click")
                    }
                }

                // Kind
                TicketCreateItem(
                    title = "Вид",
                    icon = com.example.core.R.drawable.baseline_format_list_bulleted_24
                ) {
                    SelectableText(
                        label = draft.kind?.name ?: "Выбрать вид"
                    ) {
                        TODO("Add click")
                    }
                }

                // Priority
                TicketCreateItem(
                    title = "Приоритет",
                    icon = com.example.core.R.drawable.baseline_priority_high_24
                ) {
                    SelectableText(
                        label = TicketPriorityEnum.get(draft.priority)?.title ?: "Выбрать приоритет"
                    ) {
                        TODO("Add click")
                    }
                }

                //
                // OptionalFields
                //
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
                        label = draftExecutor.value?.getFullName() ?: "Выбрать исполнителя"
                    ) {
                        TODO("Add click")
                    }
                }

                // Brigade
                TicketCreateItem(
                    title = "Бригада",
                    icon = com.example.core.R.drawable.baseline_oil_barrel_24
                ) {
                    ChipRow().Brigade(
                        draftBrigade = draftBrigade,
                        isDialogOpened = isBrigadeDialogOpened,
                    )
                }

                // PlaneDate
                TicketCreateItem(
                    title = "Плановая дата",
                    icon = com.example.core.R.drawable.baseline_oil_barrel_24
                ) {
                    SelectableText(
                        label = draft.plane_date ?: "Выбрать дату"
                    ) {
                        TODO("Add click")
                    }
                }

                // Description
                TicketCreateItem(
                    title = "Описание",
                    icon = com.example.core.R.drawable.baseline_oil_barrel_24
                ) {
                    CustomTextField(
                        text = draftDescription,
                        hint = "Ввести описание"
                    )
                }

                // AutomaticFields
                Title(
                    text = "Автоматические поля",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    modifier = Modifier.padding(top = 20.dp)
                )

                // Status
                TicketCreateItem(
                    title = "Статус",
                    icon = com.example.core.R.drawable.baseline_oil_barrel_24
                ) {
                    SelectableText(label = draftStatus.value.title)
                }

                // Author
                TicketCreateItem(
                    title = "Автор",
                    icon = com.example.core.R.drawable.baseline_oil_barrel_24
                ) {
                    SelectableText(label = authParams.user?.getFullName() ?: "[ФИО]")
                }

                // CreationDate
                val calendar = Calendar.getInstance().time
                val dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(calendar)
                TicketCreateItem(
                    title = "Дата создания",
                    icon = com.example.core.R.drawable.baseline_oil_barrel_24
                ) {
                    SelectableText(label = dateFormat)
                }
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
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.8F),
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
        label: String,
        onClick: () -> Unit = {},
    ) {
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
    @Preview
    private fun Preview() {
        AppTheme(isSystemInDarkTheme()) {
            Content()
        }
    }
}
