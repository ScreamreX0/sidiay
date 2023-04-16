package com.example.home.ui.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.domain.data_classes.entities.*
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.TicketStatuses
import com.example.home.ui.common.components.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
internal fun FacilitiesComponent(
    modifier: Modifier = Modifier,
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    ChipRowComponent(
        modifier = modifier,
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
        chips = { ticket.value.facilities?.forEach {
                CustomChip(title = it.name ?: "") {
                    ticket.value = ticket.value.copy(
                        facilities = removeFromList(ticket.value.facilities, it)
                    )
                }
            } },
        starred = starred,
        isClickable = isClickable
    )
}

@Composable
internal fun EquipmentComponent(
    modifier: Modifier = Modifier,
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    ChipRowComponent(
        modifier = modifier,
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
        starred = starred,
        isClickable = isClickable
    )
}


@Composable
internal fun TransportComponent(
    modifier: Modifier = Modifier,
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    ChipRowComponent(
        modifier = modifier,
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
        starred = starred,
        isClickable = isClickable
    )
}

@Composable
internal fun BrigadeComponent(
    modifier: Modifier = Modifier,
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    ChipRowComponent(
        modifier = modifier,
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
        starred = starred,
        isClickable = isClickable
    )
}

@Composable
internal fun NameComponent(
    modifier: Modifier = Modifier,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    TextFieldComponent(
        modifier = modifier,
        title = "Название",
        icon = R.drawable.baseline_title_24,
        text = ticket.value.name,
        onValueChange = { ticket.value = ticket.value.copy(name = it) },
        textFieldHint = "Ввести название",
        starred = starred,
        isClickable = isClickable
    )
}

@Composable
internal fun DescriptionComponent(
    modifier: Modifier = Modifier,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    TextFieldComponent(
        modifier = modifier,
        title = "Описание",
        icon = R.drawable.baseline_text_format_24,
        text = ticket.value.description,
        onValueChange = { ticket.value = ticket.value.copy(description = it) },
        textFieldHint = ticket.value.description ?: "Ввести описание",
        starred = starred,
        isClickable = isClickable
    )
}

@Composable
internal fun ServiceComponent(
    modifier: Modifier = Modifier,
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    TextWithDialogComponent(
        modifier = modifier,
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
        starred = starred,
        isClickable = isClickable
    )
}

@Composable
internal fun KindComponent(
    modifier: Modifier = Modifier,
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    TextWithDialogComponent(
        modifier = modifier,
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
        starred = starred,
        isClickable = isClickable
    )
}


@Composable
internal fun PriorityComponent(
    modifier: Modifier = Modifier,
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    TextWithDialogComponent(
        modifier = modifier,
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
        starred = starred,
        isClickable = isClickable
    )
}

@Composable
internal fun ExecutorComponent(
    modifier: Modifier = Modifier,
    ticketData: MutableState<TicketData?>,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    TextWithDialogComponent(
        modifier = modifier,
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
        starred = starred,
        isClickable = isClickable
    )
}

@Composable
internal fun StatusComponent(
    modifier: Modifier = Modifier,
    statuses: List<TicketStatuses>,
    ticket: MutableState<TicketEntity>,
    isClickable: Boolean = true
) {
    DropdownMenuComponent(
        modifier = modifier,
        items = statuses,
        onItemSelected = { ticket.value = ticket.value.copy(status = it) },
        selectedItem = ticket.value.status,
        isClickable = isClickable,
    )
}

@Composable
internal fun PlaneDateComponent(
    modifier: Modifier = Modifier,
    ticket: MutableState<TicketEntity>,
    starred: Boolean = false,
    isClickable: Boolean = true
) {
    DateFieldComponent(
        modifier = modifier,
        date = ticket.value.plane_date ?: LocalDate.now().toString(),
        onConfirmDatePicker = { ticket.value = ticket.value.copy(plane_date = it) },
        title = "Плановая дата",
        icon = R.drawable.ic_baseline_calendar_month_24,
        datePickerTitle = ticket.value.plane_date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            ?: "[Выбрать плановую дату]",
        starred = starred,
        isClickable = isClickable
    )
}

@Composable
internal fun AuthorField(
    modifier: Modifier = Modifier,
    authParams: AuthParams,
    starred: Boolean = false
) {
    TextComponent(
        modifier = modifier,
        title = "Автор",
        icon = R.drawable.ic_baseline_person_24,
        starred = starred,
        label = authParams.user?.getFullName() ?: "[Автор не определен]"
    )
}

@Composable
private fun TextComponent(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int,
    starred: Boolean,
    label: String,
) {
    TicketComponent(
        modifier = modifier,
        title = title,
        icon = icon,
        starred = starred,
        item = { CustomSelectableText(label = label) }
    )
}

@Composable
private fun DateFieldComponent(
    modifier: Modifier = Modifier,
    date: String,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    onConfirmDatePicker: (String) -> Unit,
    title: String,
    icon: Int,
    starred: Boolean = remember { false },
    datePickerTitle: String,
    isClickable: Boolean
) {
    CustomDatePicker(
        date = date,
        isDialogOpened = isDialogOpened,
        onConfirm = onConfirmDatePicker
    )
    TicketComponent(
        modifier = modifier,
        title = title,
        icon = icon,
        starred = starred,
        item = {
            CustomSelectableText(
                label = datePickerTitle,
                onClick = { if (isClickable) isDialogOpened.value = true }
            )
        }
    )
}

@Composable
private fun TextFieldComponent(
    modifier: Modifier = Modifier,
    // Title
    title: String,
    starred: Boolean,
    icon: Int,
    // Field
    text: String?,
    onValueChange: (newValue: String) -> Unit,
    textFieldHint: String,
    isClickable: Boolean
) {
    TicketComponent(
        modifier = modifier,
        title = title,
        icon = icon,
        starred = starred,
        item = {
            CustomTextField(
                text = text,
                onValueChange = onValueChange,
                hint = textFieldHint,
                isClickable = isClickable
            )
        }
    )
}

@Composable
private fun <T> ChipRowComponent(
    modifier: Modifier = Modifier,
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
    starred: Boolean,
    addingChipTitle: String,
    chips: @Composable () -> Unit,
    isClickable: Boolean,
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
        modifier = modifier,
        title = title,
        icon = icon,
        starred = starred
    ) {
        CustomChipRow(
            modifier = modifier,
            addingChipTitle = addingChipTitle,
            isDialogOpened = isDialogOpened,
            chips = { chips() },
            isClickable = isClickable
        )
    }
}

@Composable
private fun <T> TextWithDialogComponent(
    modifier: Modifier = Modifier,
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
    starred: Boolean,
    isClickable: Boolean,
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
        modifier = modifier,
        title = title,
        icon = icon,
        starred = starred
    ) {
        CustomSelectableText(
            label = label,
            onClick = { if (isClickable) isDialogOpened.value = true }
        )
    }
}

@Composable
fun DropdownMenuComponent(
    modifier: Modifier,
    items: List<TicketStatuses>,
    onItemSelected: (TicketStatuses) -> Unit,
    selectedItem: TicketStatuses?,
    isClickable: Boolean,
) {
    TicketComponent(
        modifier = modifier,
        title = "Статус",
        icon = R.drawable.baseline_help_outline_24
    ) {
        DropDownComponent(
            modifier = modifier,
            items = items,
            onItemSelected = onItemSelected,
            selectedItem = selectedItem,
            isClickable = isClickable
        )
    }
}

@Composable
private fun TicketComponent(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int,
    starred: Boolean = remember { false },
    item: @Composable () -> Unit
) {
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
                if (starred) {
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