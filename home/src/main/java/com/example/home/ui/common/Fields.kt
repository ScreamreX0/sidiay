package com.example.home.ui.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.domain.data_classes.entities.*
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.TicketStatuses
import com.example.home.ui.common.components.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
internal fun FacilitiesComponent(
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    ChipRowComponent(
        dialogTitle = "Выберите объект",
        ticketData = ticketData.value?.facilities,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                if (ticket.value.facilities?.contains(it) != true) {
                    ticket.value = ticket.value.copy(
                        facilities = addToList(ticket.value.facilities, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        title = "Объекты",
        icon = R.drawable.baseline_oil_barrel_24,
        addingChipTitle = "Добавить объекты",
        chips = {
            ticket.value.facilities?.forEach {
                CustomChip(title = it.name ?: "") {
                    ticket.value = ticket.value.copy(
                        facilities = removeFromList(ticket.value.facilities, it)
                    )
                }
            }
        },
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
internal fun EquipmentComponent(
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    ChipRowComponent(
        dialogTitle = "Выберите оборудование",
        ticketData = ticketData.value?.equipment,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                if (ticket.value.equipment?.contains(it) != true) {
                    ticket.value = ticket.value.copy(
                        equipment = addToList(ticket.value.equipment, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        title = "Оборудование",
        icon = R.drawable.baseline_computer_24,
        addingChipTitle = "Добавить оборудование",
        chips = {
            ticket.value.equipment?.forEach {
                CustomChip(title = it.name ?: "") {
                    ticket.value = ticket.value.copy(
                        equipment = removeFromList(ticket.value.equipment, it)
                    )
                }
            }
        },
        ticketFieldsParams = ticketFieldsParams,
    )
}


@Composable
internal fun TransportComponent(
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    ChipRowComponent(
        dialogTitle = "Выберите транспорт",
        ticketData = ticketData.value?.transport,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                if (ticket.value.transport?.contains(it) != true) {
                    ticket.value = ticket.value.copy(
                        transport = addToList(ticket.value.transport, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        title = "Транспорт",
        icon = R.drawable.baseline_oil_barrel_24,
        addingChipTitle = "Добавить транспорт",
        chips = {
            ticket.value.transport?.forEach {
                CustomChip(title = it.name ?: "") {
                    ticket.value = ticket.value.copy(
                        transport = removeFromList(ticket.value.transport, it)
                    )
                }
            }
        },
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
internal fun BrigadeComponent(
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    ChipRowComponent(
        dialogTitle = "Выберите сотрудника",
        ticketData = ticketData.value?.users,
        predicate = { it, searchTextState ->
            it.employee?.name?.contains(searchTextState.text, true) ?: false
                    || it.employee?.firstname?.contains(searchTextState.text, true) ?: false
                    || it.employee?.lastname?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.getFullName() ?: "[ФИО]") {
                if (ticket.value.brigade?.contains(it) != true) {
                    ticket.value = ticket.value.copy(
                        brigade = addToList(ticket.value.brigade, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        title = "Бригада",
        icon = R.drawable.baseline_people_24,
        addingChipTitle = "Добавить сотрудников",
        chips = {
            ticket.value.brigade?.forEach {
                CustomChip(title = it.getFullName() ?: "") {
                    ticket.value = ticket.value.copy(
                        brigade = removeFromList(ticket.value.brigade, it)
                    )
                }
            }
        },
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
internal fun NameComponent(
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    TextFieldComponent(
        title = "Название",
        icon = R.drawable.baseline_title_24,
        text = ticket.value.name,
        onValueChange = { ticket.value = ticket.value.copy(name = it) },
        textFieldHint = "Ввести название",
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
internal fun DescriptionComponent(
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    TextFieldComponent(
        title = "Описание",
        icon = R.drawable.baseline_text_format_24,
        text = ticket.value.description,
        onValueChange = { ticket.value = ticket.value.copy(description = it) },
        textFieldHint = ticket.value.description ?: "Ввести описание",
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
internal fun ServiceComponent(
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    TextWithDialogComponent(
        dialogTitle = "Выберите сервис",
        ticketData = ticketData.value?.services,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                ticket.value = ticket.value.copy(service = it)
                isDialogOpened.value = false
            }
        },
        title = "Сервисы",
        label = ticket.value.service?.name ?: "[Выбрать сервис]",
        icon = R.drawable.baseline_format_list_bulleted_24,
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
internal fun KindComponent(
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    TextWithDialogComponent(
        dialogTitle = "Выберите вид",
        ticketData = ticketData.value?.kinds,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                ticket.value = ticket.value.copy(kind = it)
                isDialogOpened.value = false
            }
        },
        title = "Вид",
        label = ticket.value.kind?.name ?: "[Выбрать вид]",
        icon = R.drawable.baseline_format_list_bulleted_24,
        ticketFieldsParams = ticketFieldsParams,
    )
}


@Composable
internal fun PriorityComponent(
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    TextWithDialogComponent(
        dialogTitle = "Выберите приоритет",
        ticketData = ticketData.value?.priorities,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                ticket.value = ticket.value.copy(priority = it)
                isDialogOpened.value = false
            }
        },
        title = "Приоритет",
        label = ticket.value.priority?.name ?: "[Выбрать приоритет]",
        icon = R.drawable.baseline_priority_high_24,
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
internal fun ExecutorComponent(
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    TextWithDialogComponent(
        dialogTitle = "Выберите исполнителя",
        ticketData = ticketData.value?.users,
        predicate = { it, searchTextState ->
            it.employee?.name?.contains(searchTextState.text, true) ?: false
                    || it.employee?.firstname?.contains(searchTextState.text, true) ?: false
                    || it.employee?.lastname?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.getFullName() ?: "[ФИО]") {
                ticket.value = ticket.value.copy(executor = it)
                isDialogOpened.value = false
            }
        },
        title = "Исполнитель",
        label = ticket.value.executor?.getFullName() ?: "[Выбрать исполнителя]",
        icon = R.drawable.ic_baseline_person_24,
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
internal fun StatusComponent(
    statuses: List<TicketStatuses>,
    selectedTicketStatus: MutableState<TicketStatuses>,
    ticketFieldsParams: TicketFieldParams,
    updateRestrictions: () -> Unit
) {
    DropdownMenuComponent(
        items = statuses,
        onItemSelected = {
            selectedTicketStatus.value = it
            updateRestrictions()
        },
        selectedItem = selectedTicketStatus.value,
        title = "Статус",
        icon = R.drawable.baseline_help_outline_24,
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
internal fun PlaneDateComponent(
    ticket: MutableState<TicketEntity>,
    ticketFieldsParams: TicketFieldParams
) {
    DateFieldComponent(
        date = ticket.value.plane_date ?: LocalDate.now().toString(),
        onConfirmDatePicker = { ticket.value = ticket.value.copy(plane_date = it) },
        title = "Плановая дата",
        icon = R.drawable.ic_baseline_calendar_month_24,
        datePickerTitle = ticket.value.plane_date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            ?: "[Выбрать плановую дату]",
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
internal fun AuthorComponent(
    authParams: AuthParams,
    ticketFieldsParams: TicketFieldParams
) {
    TextComponent(
        title = "Автор",
        icon = R.drawable.ic_baseline_person_24,
        label = authParams.user?.getFullName() ?: "[Автор не определен]",
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
private fun DropdownMenuComponent(
    items: List<TicketStatuses>,
    onItemSelected: (TicketStatuses) -> Unit,
    selectedItem: TicketStatuses?,
    title: String,
    icon: Int,
    ticketFieldsParams: TicketFieldParams
) {
    TicketComponent(
        title = title,
        icon = icon,
        item = {
            val expanded = remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { if (ticketFieldsParams.isClickable) expanded.value = true }
            ) {
                Text(
                    text = selectedItem?.title ?: "[Статус не определен]",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.8F),
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                onItemSelected(item)
                                expanded.value = false
                            }) {
                            Text(
                                text = item.title,
                                fontSize = MaterialTheme.typography.h5.fontSize,
                                color = MaterialTheme.colors.onBackground.copy(alpha = 0.8F),
                            )
                        }
                    }
                }
            }
        },
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
private fun TextComponent(
    title: String,
    icon: Int,
    label: String,
    ticketFieldsParams: TicketFieldParams
) {
    TicketComponent(
        title = title,
        icon = icon,
        item = { CustomText(label = label) },
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
private fun DateFieldComponent(
    date: String,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    onConfirmDatePicker: (String) -> Unit,
    title: String,
    icon: Int,
    datePickerTitle: String,
    ticketFieldsParams: TicketFieldParams
) {
    CustomDatePicker(
        date = date,
        isDialogOpened = isDialogOpened,
        onConfirm = onConfirmDatePicker
    )
    TicketComponent(
        title = title,
        icon = icon,
        item = {
            CustomText(
                label = datePickerTitle,
                onClick = { if (ticketFieldsParams.isClickable) isDialogOpened.value = true }
            )
        },
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
private fun TextFieldComponent(
    title: String,
    icon: Int,
    text: String?,
    onValueChange: (newValue: String) -> Unit,
    textFieldHint: String,
    ticketFieldsParams: TicketFieldParams
) {
    TicketComponent(
        title = title,
        icon = icon,
        item = {
            CustomTextField(
                text = text,
                onValueChange = onValueChange,
                hint = textFieldHint,
                isClickable = ticketFieldsParams.isClickable
            )
        },
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
private fun <T> ChipRowComponent(
    // Dialog
    dialogTitle: String,
    ticketData: List<T>?,
    predicate: (T, TextFieldValue) -> Boolean,
    listItem: @Composable (T, isDialogOpened: MutableState<Boolean>) -> Unit,
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    scrollState: ScrollState = rememberScrollState(),
    // Chip row
    title: String,
    icon: Int,
    addingChipTitle: String,
    chips: @Composable () -> Unit,
    ticketFieldsParams: TicketFieldParams
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = dialogTitle,
        scrollState = scrollState,
        fields = ticketData,
        predicate = predicate,
        listItem = { listItem(it, isDialogOpened) },
        searchTextState = searchTextState
    )
    TicketComponent(
        title = title,
        icon = icon,
        item = {
            CustomChipRow(
                addingChipTitle = addingChipTitle,
                isDialogOpened = isDialogOpened,
                chips = { chips() },
                isClickable = ticketFieldsParams.isClickable
            )
        },
        ticketFieldsParams = ticketFieldsParams,
    )
}

@Composable
private fun <T> TextWithDialogComponent(
    // Dialog
    dialogTitle: String,
    ticketData: List<T>?,
    predicate: (T, TextFieldValue) -> Boolean,
    listItem: @Composable (T, isDialogOpened: MutableState<Boolean>) -> Unit,
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    scrollState: ScrollState = rememberScrollState(),
    // Clickable text
    icon: Int,
    title: String,
    label: String,
    ticketFieldsParams: TicketFieldParams
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = dialogTitle,
        scrollState = scrollState,
        fields = ticketData,
        predicate = predicate,
        listItem = { listItem(it, isDialogOpened) },
        searchTextState = searchTextState,
    )
    TicketComponent(
        title = title,
        icon = icon,
        item = {
            CustomText(
                label = label,
                onClick = { if (ticketFieldsParams.isClickable) isDialogOpened.value = true }
            )
        },
        ticketFieldsParams = ticketFieldsParams,
    )
}


@Composable
private fun TicketComponent(
    title: String,
    icon: Int,
    item: @Composable () -> Unit,
    ticketFieldsParams: TicketFieldParams
) {
    val modifier = if (ticketFieldsParams.isClickable) Modifier else Modifier.alpha(0.6F)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
    ) {
        Icon(
            modifier = modifier
                .height(45.dp)
                .width(45.dp)
                .padding(end = 10.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground
        )
        Column {
            Row {
                Text(
                    modifier = modifier.padding(bottom = 5.dp),
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = MaterialTheme.typography.h3.fontSize
                )
                if (ticketFieldsParams.starred) {
                    Text(
                        modifier = modifier.padding(start = 5.dp),
                        text = "*",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = MaterialTheme.typography.h3.fontSize
                    )
                }
            }
            item.invoke()
        }
    }
}

private fun <T> addToList(list: List<T>?, addingItem: T): List<T> {
    val newList = list?.toMutableList() ?: mutableListOf()
    newList.add(addingItem)
    return newList.toList()
}

private fun <T> removeFromList(list: List<T>?, removingItem: T): List<T> {
    val newList = list?.toMutableList() ?: mutableListOf()
    newList.remove(removingItem)
    return newList.toList()
}