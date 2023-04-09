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
import com.example.home.ui.common.components.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
internal fun FacilitiesField(
    ticketData: MutableState<TicketData?>,
    draft: MutableState<TicketEntity>,
    starred: Boolean = false
) {
    CustomChipRowField(
        dialogTitle = "Выберите объект",
        ticketData = ticketData.value?.facilities,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                if (draft.value.facilities?.contains(it) != true) {
                    draft.value = draft.value.copy(
                        facilities = addToList(draft.value.facilities, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        title = "Объекты",
        icon = R.drawable.baseline_oil_barrel_24,
        addingChipTitle = "Добавить объекты",
        chips = {
            draft.value.facilities?.forEach {
                CustomChip(title = it.name ?: "") {
                    draft.value = draft.value.copy(
                        facilities = removeFromList(draft.value.facilities, it)
                    )
                }
            }
        },
        starred = starred
    )
}

@Composable
internal fun EquipmentField(
    ticketData: MutableState<TicketData?>,
    draft: MutableState<TicketEntity>,
    starred: Boolean = false
) {
    CustomChipRowField(
        dialogTitle = "Выберите оборудование",
        ticketData = ticketData.value?.equipment,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                if (draft.value.equipment?.contains(it) != true) {
                    draft.value = draft.value.copy(
                        equipment = addToList(draft.value.equipment, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        title = "Оборудование",
        icon = R.drawable.baseline_computer_24,
        addingChipTitle = "Добавить оборудование",
        chips = {
            draft.value.equipment?.forEach {
                CustomChip(title = it.name ?: "") {
                    draft.value = draft.value.copy(
                        equipment = removeFromList(draft.value.equipment, it)
                    )
                }
            }
        },
        starred = starred
    )
}


@Composable
internal fun TransportField(
    ticketData: MutableState<TicketData?>,
    draft: MutableState<TicketEntity>,
    starred: Boolean = false
) {
    CustomChipRowField(
        dialogTitle = "Выберите транспорт",
        ticketData = ticketData.value?.transport,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                if (draft.value.transport?.contains(it) != true) {
                    draft.value = draft.value.copy(
                        transport = addToList(draft.value.transport, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        title = "Транспорт",
        icon = R.drawable.baseline_oil_barrel_24,
        addingChipTitle = "Добавить транспорт",
        chips = {
            draft.value.transport?.forEach {
                CustomChip(title = it.name ?: "") {
                    draft.value = draft.value.copy(
                        transport = removeFromList(draft.value.transport, it)
                    )
                }
            }
        },
        starred = starred
    )
}

@Composable
internal fun BrigadeField(
    ticketData: MutableState<TicketData?>,
    draft: MutableState<TicketEntity>,
    starred: Boolean = false
) {
    CustomChipRowField(
        dialogTitle = "Выберите сотрудника",
        ticketData = ticketData.value?.users,
        predicate = { it, searchTextState ->
            it.employee?.name?.contains(searchTextState.text, true) ?: false
                    || it.employee?.firstname?.contains(searchTextState.text, true) ?: false
                    || it.employee?.lastname?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.getFullName() ?: "[ФИО]") {
                if (draft.value.brigade?.contains(it) != true) {
                    draft.value = draft.value.copy(
                        brigade = addToList(draft.value.brigade, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        title = "Бригада",
        icon = R.drawable.baseline_people_24,
        addingChipTitle = "Добавить сотрудников",
        chips = {
            draft.value.brigade?.forEach {
                CustomChip(title = it.getFullName() ?: "") {
                    draft.value = draft.value.copy(
                        brigade = removeFromList(draft.value.brigade, it)
                    )
                }
            }
        },
        starred = starred
    )
}

@Composable
internal fun NameField(draft: MutableState<TicketEntity>, starred: Boolean = false) {
    CustomTextField(
        title = "Название",
        icon = R.drawable.baseline_title_24,
        text = draft.value.name,
        onValueChange = { draft.value = draft.value.copy(name = it) },
        textFieldHint = "Ввести название",
        starred = starred
    )
}

@Composable
internal fun DescriptionField(draft: MutableState<TicketEntity>, starred: Boolean = false) {
    CustomTextField(
        title = "Описание",
        icon = R.drawable.baseline_text_format_24,
        text = draft.value.description,
        onValueChange = { draft.value = draft.value.copy(description = it) },
        textFieldHint = draft.value.description ?: "Ввести описание",
        starred = starred
    )
}

@Composable
internal fun ServiceField(
    ticketData: MutableState<TicketData?>,
    draft: MutableState<TicketEntity>,
    starred: Boolean = false
) {
    CustomClickableField(
        dialogTitle = "Выберите сервис",
        ticketData = ticketData.value?.services,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                draft.value = draft.value.copy(service = it)
                isDialogOpened.value = false
            }
        },
        title = "Сервисы",
        textIfLabelIsNull = "Выбрать сервис",
        label = draft.value.service?.name,
        icon = R.drawable.baseline_format_list_bulleted_24,
        starred = starred
    )
}

@Composable
internal fun KindField(
    ticketData: MutableState<TicketData?>,
    draft: MutableState<TicketEntity>,
    starred: Boolean = false
) {
    CustomClickableField(
        dialogTitle = "Выберите вид",
        ticketData = ticketData.value?.kinds,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                draft.value = draft.value.copy(kind = it)
                isDialogOpened.value = false
            }
        },
        title = "Вид",
        textIfLabelIsNull = "Выбрать вид",
        label = draft.value.kind?.name,
        icon = R.drawable.baseline_format_list_bulleted_24,
        starred = starred
    )
}


@Composable
internal fun PriorityField(
    ticketData: MutableState<TicketData?>,
    draft: MutableState<TicketEntity>,
    starred: Boolean = false
) {
    CustomClickableField(
        dialogTitle = "Выберите приоритет",
        ticketData = ticketData.value?.priorities,
        predicate = { it, searchTextState ->
            it.name?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.name ?: "") {
                draft.value = draft.value.copy(priority = it)
                isDialogOpened.value = false
            }
        },
        title = "Приоритет",
        textIfLabelIsNull = "Выбрать приоритет",
        label = draft.value.priority?.name,
        icon = R.drawable.baseline_priority_high_24,
        starred = starred
    )
}

@Composable
internal fun ExecutorField(
    ticketData: MutableState<TicketData?>,
    draft: MutableState<TicketEntity>,
    starred: Boolean = false
) {
    CustomClickableField(
        dialogTitle = "Выберите сотрудника",
        ticketData = ticketData.value?.users,
        predicate = { it, searchTextState ->
            it.employee?.name?.contains(searchTextState.text, true) ?: false
                    || it.employee?.firstname?.contains(searchTextState.text, true) ?: false
                    || it.employee?.lastname?.contains(searchTextState.text, true) ?: false
        },
        listItem = { it, isDialogOpened ->
            ListElement(title = it.getFullName() ?: "[ФИО]") {
                draft.value = draft.value.copy(executor = it)
                isDialogOpened.value = false
            }
        },
        title = "Исполнитель",
        textIfLabelIsNull = "Выбрать исполнителя",
        label = draft.value.executor?.getFullName(),
        icon = R.drawable.ic_baseline_person_24,
        starred = starred
    )
}

@Composable
internal fun PlaneDateField(
    draft: MutableState<TicketEntity>,
    starred: Boolean = false
) {
    CustomDateField(
        date = draft.value.plane_date ?: LocalDate.now().toString(),
        onConfirmDatePicker = { draft.value = draft.value.copy(plane_date = it) },
        title = "Плановая дата",
        icon = R.drawable.ic_baseline_calendar_month_24,
        datePickerTitle = draft.value.plane_date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
        datePickerTitleNull = "Выбрать плановую дату",
        starred = starred
    )
}

@Composable
internal fun StatusField(
    draft: MutableState<TicketEntity>,
    starred: Boolean = false
) {
    CustomFieldWithText(
        title = "Статус",
        icon = R.drawable.ic_baseline_playlist_add_check_24,
        starred = starred,
        label = draft.value.status?.name ?: "",
        labelIfNull = "Выбрать статус"
    )
}

@Composable
internal fun AuthorField(
    authParams: AuthParams,
    starred: Boolean = false
) {
    CustomFieldWithText(
        title = "Автор",
        icon = R.drawable.ic_baseline_person_24,
        starred = starred,
        label = authParams.user?.getFullName(),
        labelIfNull = "Автор не определен"
    )
}

@Composable
private fun CustomFieldWithText(
    title: String,
    icon: Int,
    starred: Boolean,
    label: String?,
    labelIfNull: String
) {
    TicketCreateItem(
        title = title,
        icon = icon,
        starred = starred
    ) {
        CustomSelectableText(
            label = label,
            nullLabel = labelIfNull
        )
    }
}

@Composable
private fun CustomDateField(
    date: String,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    onConfirmDatePicker: (String) -> Unit,
    title: String,
    icon: Int,
    starred: Boolean = remember { false },
    datePickerTitle: String?,
    datePickerTitleNull: String,
) {
    CustomDatePicker(
        date = date,
        isDialogOpened = isDialogOpened,
        onConfirm = onConfirmDatePicker
    )
    TicketCreateItem(
        title = title,
        icon = icon,
        starred = starred
    ) {
        CustomSelectableText(
            label = datePickerTitle,
            nullLabel = datePickerTitleNull
        ) { isDialogOpened.value = true }
    }
}

@Composable
private fun CustomTextField(
    // Title
    title: String,
    starred: Boolean,
    icon: Int,
    // Field
    text: String?,
    onValueChange: (newValue: String) -> Unit,
    textFieldHint: String,
) {
    TicketCreateItem(
        title = title,
        icon = icon,
        starred = starred
    ) {
        CustomTextField(
            text = text,
            onValueChange = onValueChange,
            hint = textFieldHint
        )
    }
}

@Composable
private fun <T> CustomChipRowField(
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
    chips: @Composable () -> Unit
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
    TicketCreateItem(
        title = title,
        icon = icon,
        starred = starred
    ) {
        CustomChipRow(
            modifier = Modifier,
            addingChipTitle = addingChipTitle,
            isDialogOpened = isDialogOpened
        ) { chips() }
    }
}

@Composable
private fun <T> CustomClickableField(
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
    textIfLabelIsNull: String,
    label: String?,
    starred: Boolean,
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
    TicketCreateItem(
        title = title,
        icon = icon,
        starred = starred
    ) {
        CustomSelectableText(
            nullLabel = textIfLabelIsNull,
            label = label,
        ) { isDialogOpened.value = true }
    }
}

@Composable
private fun TicketCreateItem(
    title: String,
    icon: Int,
    starred: Boolean = remember { false },
    item: @Composable () -> Unit,
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
            Row {
                // Subtitle
                Text(
                    modifier = Modifier
                        .padding(bottom = 5.dp),
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = MaterialTheme.typography.h3.fontSize
                )
                if (starred) {
                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = "*",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = MaterialTheme.typography.h3.fontSize
                    )
                }
            }

            // Item
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