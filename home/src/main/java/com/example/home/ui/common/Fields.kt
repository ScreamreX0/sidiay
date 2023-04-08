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
import androidx.compose.ui.unit.sp
import com.example.core.R
import com.example.domain.data_classes.entities.*
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketData
import com.example.home.ui.common.components.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
internal fun FacilitiesField(
    draft: MutableState<DraftEntity>,
    ticketData: MutableState<TicketData?>,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    scrollState: ScrollState = rememberScrollState(),
    starred: Boolean = remember { false }
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите объект",
        scrollState = scrollState,
        fields = ticketData.value?.facilities,
        predicate = { it.name?.contains(searchTextState.value.text, true) ?: false },
        listItem = {
            ListElement(title = it.name ?: "") {
                if (draft.value.facilities?.contains(it) != true) {
                    draft.value = draft.value.copy(
                        facilities = addToList(draft.value.facilities, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
    TicketCreateItem(
        title = "Объекты",
        icon = R.drawable.baseline_oil_barrel_24,
        starred = starred
    ) {
        CustomChipRow(
            modifier = Modifier,
            addingChipTitle = "Добавить объекты",
            isDialogOpened = isDialogOpened
        ) {
            draft.value.facilities?.forEach {
                CustomChip(title = it.name ?: "") {
                    draft.value = draft.value.copy(
                        facilities = removeFromList(draft.value.facilities, it)
                    )
                }
            }
        }
    }
}

@Composable
internal fun EquipmentField(
    draft: MutableState<DraftEntity>,
    ticketData: MutableState<TicketData?>,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    scrollState: ScrollState = rememberScrollState(),
    starred: Boolean = remember { false }
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите оборудование",
        scrollState = scrollState,
        fields = ticketData.value?.equipment,
        predicate = { it.name?.contains(searchTextState.value.text, true) ?: false },
        listItem = {
            ListElement(title = it.name ?: "") {
                if (draft.value.equipment?.contains(it) != true) {
                    draft.value = draft.value.copy(
                        equipment = addToList(draft.value.equipment, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
    TicketCreateItem(
        title = "Оборудование",
        icon = R.drawable.baseline_computer_24,
        starred = starred
    ) {
        CustomChipRow(
            modifier = Modifier,
            addingChipTitle = "Добавить оборудование",
            isDialogOpened = isDialogOpened,
        ) {
            draft.value.equipment?.forEach {
                CustomChip(title = it.name ?: "") {
                    draft.value = draft.value.copy(
                        equipment = removeFromList(draft.value.equipment, it)
                    )
                }
            }
        }
    }
}

@Composable
internal fun TransportField(
    draft: MutableState<DraftEntity>,
    ticketData: MutableState<TicketData?>,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    scrollState: ScrollState = rememberScrollState(),
    starred: Boolean = remember { false }
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите транспорт",
        scrollState = scrollState,
        fields = ticketData.value?.transport,
        predicate = { it.name?.contains(searchTextState.value.text, true) ?: false },
        listItem = {
            ListElement(title = it.name ?: "") {
                if (draft.value.transport?.contains(it) != true) {
                    draft.value = draft.value.copy(
                        transport = addToList(draft.value.transport, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
    TicketCreateItem(
        title = "Транспорт",
        icon = R.drawable.baseline_directions_car_24,
        starred = starred
    ) {
        CustomChipRow(
            modifier = Modifier,
            addingChipTitle = "Добавить транспорт",
            isDialogOpened = isDialogOpened
        ) {
            draft.value.transport?.forEach {
                CustomChip(title = it.name ?: "") {
                    draft.value = draft.value.copy(
                        transport = removeFromList(draft.value.transport, it)
                    )
                }
            }
        }
    }
}

@Composable
internal fun ServiceField(
    draft: MutableState<DraftEntity>,
    ticketData: MutableState<TicketData?>,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    scrollState: ScrollState = rememberScrollState(),
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    starred: Boolean = remember { false }
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите сервис",
        scrollState = scrollState,
        fields = ticketData.value?.services,
        predicate = { it.name?.contains(searchTextState.value.text, true) ?: false },
        listItem = {
            ListElement(title = it.name ?: "") {
                draft.value = draft.value.copy(service = it)
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
    TicketCreateItem(
        title = "Сервисы",
        icon = R.drawable.ic_baseline_miscellaneous_services_24,
        starred = starred
    ) {
        CustomSelectableText(
            nullLabel = "Выбрать сервис",
            label = draft.value.service?.name
        ) { isDialogOpened.value = true }
    }
}

@Composable
internal fun KindField(
    draft: MutableState<DraftEntity>,
    ticketData: MutableState<TicketData?>,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    scrollState: ScrollState = rememberScrollState(),
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    starred: Boolean = remember { false }
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите вид",
        scrollState = scrollState,
        fields = ticketData.value?.kinds,
        predicate = { it.name?.contains(searchTextState.value.text, true) ?: false },
        listItem = {
            ListElement(title = it.name ?: "") {
                draft.value = draft.value.copy(kind = it)
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
    TicketCreateItem(
        title = "Вид",
        icon = R.drawable.baseline_format_list_bulleted_24,
        starred = starred
    ) {
        CustomSelectableText(
            label = draft.value.kind?.name,
            nullLabel = "Выбрать вид"
        ) { isDialogOpened.value = true }
    }
}

@Composable
internal fun PriorityField(
    draft: MutableState<DraftEntity>,
    ticketData: MutableState<TicketData?>,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    scrollState: ScrollState = rememberScrollState(),
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    starred: Boolean = remember { false }
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите приоритет",
        scrollState = scrollState,
        fields = ticketData.value?.priorities,
        predicate = { it.name?.contains(searchTextState.value.text, true) ?: false },
        listItem = {
            ListElement(title = it.name ?: "") {
                draft.value = draft.value.copy(priority = it)
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
    TicketCreateItem(
        title = "Приоритет",
        icon = R.drawable.baseline_priority_high_24,
        starred = starred
    ) {
        CustomSelectableText(
            label = draft.value.priority?.name,
            nullLabel = "Выбрать приоритет",
        ) { isDialogOpened.value = true }
    }
}

@Composable
internal fun NameField(draft: MutableState<DraftEntity>, starred: Boolean = remember { false }) {
    TicketCreateItem(
        title = "Название",
        icon = R.drawable.baseline_title_24,
        starred = starred
    ) {
        CustomTextField(
            text = draft.value.name,
            onValueChange = { draft.value = draft.value.copy(name = it) },
            hint = "Ввести название"
        )
    }
}

@Composable
internal fun ExecutorField(
    draft: MutableState<DraftEntity>,
    ticketData: MutableState<TicketData?>,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    scrollState: ScrollState = rememberScrollState(),
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    starred: Boolean = remember { false }
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите сотрудника",
        scrollState = scrollState,
        fields = ticketData.value?.users,
        predicate = {
            it.employee?.name?.contains(searchTextState.value.text, true) ?: false
                    || it.employee?.firstname?.contains(searchTextState.value.text, true) ?: false
                    || it.employee?.lastname?.contains(searchTextState.value.text, true) ?: false
        },
        listItem = {
            ListElement(title = it.getFullName() ?: "[ФИО]") {
                draft.value = draft.value.copy(executor = it)
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
    TicketCreateItem(
        title = "Исполнитель",
        icon = R.drawable.ic_baseline_person_24,
        starred = starred
    ) {
        CustomSelectableText(
            label = draft.value.executor?.getFullName(),
            nullLabel = "Выбрать исполнителя"
        ) { isDialogOpened.value = true }
    }
}

@Composable
internal fun BrigadeField(
    draft: MutableState<DraftEntity>,
    ticketData: MutableState<TicketData?>,
    isDialogOpened: MutableState<Boolean> = remember { mutableStateOf(false) },
    scrollState: ScrollState = rememberScrollState(),
    searchTextState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    starred: Boolean = remember { false }
) {
    CustomDialog(
        isDialogOpened = isDialogOpened,
        topAppBarTitle = "Выберите сотрудника",
        scrollState = scrollState,
        fields = ticketData.value?.users,
        predicate = {
            it.employee?.name?.contains(searchTextState.value.text, true) ?: false
                    || it.employee?.firstname?.contains(searchTextState.value.text, true) ?: false
                    || it.employee?.lastname?.contains(searchTextState.value.text, true) ?: false
        },
        listItem = {
            ListElement(title = it.getFullName() ?: "[ФИО]") {
                if (draft.value.brigade?.contains(it) != true) {
                    draft.value = draft.value.copy(
                        brigade = addToList(draft.value.brigade, it)
                    )
                }
                isDialogOpened.value = false
            }
        },
        searchTextState = searchTextState,
    )
    TicketCreateItem(
        title = "Бригада",
        icon = R.drawable.baseline_people_24,
        starred = starred
    ) {
        CustomChipRow(
            modifier = Modifier,
            addingChipTitle = "Добавить сотрудников",
            isDialogOpened = isDialogOpened
        ) {
            draft.value.brigade?.forEach {
                CustomChip(title = it.getFullName() ?: "") {
                    draft.value = draft.value.copy(
                        brigade = removeFromList(draft.value.brigade, it)
                    )
                }
            }
        }
    }
}

@Composable
internal fun PlaneDateField(
    draft: MutableState<DraftEntity>,
    starred: Boolean = remember { false }
) {
    val isDialogOpened = remember { mutableStateOf(false) }
    CustomDatePicker(
        date = draft.value.plane_date ?: LocalDate.now()                                                                                      ,
        isDialogOpened = isDialogOpened,
        onConfirm = { date -> draft.value = draft.value.copy(plane_date = date) }
    )
    TicketCreateItem(
        title = "Плановая дата",
        icon = R.drawable.ic_baseline_calendar_month_24,
        starred = starred
    ) {
        CustomSelectableText(
            label = draft.value.plane_date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            nullLabel = "Выбрать плановую дату"
        ) { isDialogOpened.value = true }
    }
}

@Composable
internal fun DescriptionField(
    draft: MutableState<DraftEntity>,
    starred: Boolean = remember { false }
) {
    TicketCreateItem(
        title = "Описание",
        icon = R.drawable.baseline_text_format_24,
        starred = starred
    ) {
        CustomTextField(
            text = draft.value.description,
            onValueChange = { draft.value = draft.value.copy(description = it) },
            hint = draft.value.description ?: "Ввести описание"
        )
    }
}

@Composable
internal fun StatusField(draft: MutableState<DraftEntity>, starred: Boolean = remember { false }) {
    TicketCreateItem(
        title = "Статус",
        icon = R.drawable.ic_baseline_playlist_add_check_24,
        starred = starred
    ) {
        CustomSelectableText(
            label = draft.value.status?.name ?: "",
            nullLabel = "Выбрать статус"
        )
    }
}

@Composable
internal fun AuthorField(authParams: AuthParams, starred: Boolean = remember { false }) {
    TicketCreateItem(
        title = "Автор",
        icon = R.drawable.ic_baseline_person_24,
        starred = starred
    ) {
        CustomSelectableText(
            label = authParams.user?.getFullName(),
            nullLabel = "Автор не определен"
        )
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