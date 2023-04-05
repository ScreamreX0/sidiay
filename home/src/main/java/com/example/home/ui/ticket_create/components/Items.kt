package com.example.home.ui.ticket_create.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.data_classes.entities.DraftEntity
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.AuthParams
import com.example.domain.data_classes.params.TicketData
import java.time.format.DateTimeFormatter

@Composable
internal fun RequiredFieldsTitleComponent() {
    CustomTitle(
        modifier = Modifier
            .padding(top = 20.dp),
        text = "Обязательные поля",
        fontSize = MaterialTheme.typography.h5.fontSize
    )
}

@Composable
internal fun FacilitiesComponent(draft: DraftEntity, fields: MutableState<TicketData?>) {
    val draftFacilities: MutableList<FacilityEntity?> = remember {
        draft.facilities?.toMutableStateList() ?: mutableStateListOf()
    }
    val isDialogOpened = remember { mutableStateOf(false) }
    val facilitiesScrollState = rememberScrollState()
    FacilitiesDialog(
        scrollState = facilitiesScrollState,
        isDialogOpened = isDialogOpened,
        fields = fields,
        draftFacilities = draftFacilities,
    )
    TicketCreateItem(
        title = "Объекты",
        icon = com.example.core.R.drawable.baseline_oil_barrel_24
    ) {
        CustomChipRow(
            modifier = Modifier,
            addingChipTitle = "Добавить объекты",
            isDialogOpened = isDialogOpened
        ) {
            draftFacilities.forEach {
                CustomChip(title = it?.name ?: "") { draftFacilities.remove(it) }
            }
        }
    }
}

@Composable
internal fun ServicesComponent(draft: DraftEntity, fields: MutableState<TicketData?>) {
    val draftService = remember { mutableStateOf(draft.service) }
    val isDialogOpened = remember { mutableStateOf(false) }
    val servicesScrollState = rememberScrollState()
    ServiceDialog(
        scrollState = servicesScrollState,
        isDialogOpened = isDialogOpened,
        fields = fields,
        draftService = draftService
    )
    TicketCreateItem(
        title = "Сервисы",
        icon = com.example.core.R.drawable.ic_baseline_miscellaneous_services_24
    ) {
        CustomSelectableText(
            nullLabel = "Выбрать сервис",
            label = draftService.value?.name
        ) { isDialogOpened.value = true }
    }
}

@Composable
internal fun KindComponent(draft: DraftEntity, fields: MutableState<TicketData?>) {
    val draftKind = remember { mutableStateOf(draft.kind) }
    val isDialogOpened = remember { mutableStateOf(false) }
    val kindsScrollState = rememberScrollState()
    KindDialog(
        scrollState = kindsScrollState,
        isDialogOpened = isDialogOpened,
        fields = fields,
        draftKinds = draftKind,
    )
    TicketCreateItem(
        title = "Вид",
        icon = com.example.core.R.drawable.baseline_format_list_bulleted_24
    ) {
        CustomSelectableText(
            label = draftKind.value?.name,
            nullLabel = "Выбрать вид"
        ) { isDialogOpened.value = true }
    }
}

@Composable
internal fun PriorityComponent(draft: DraftEntity, fields: MutableState<TicketData?>) {
    val draftPriority = remember { mutableStateOf(draft.priority) }
    val isDialogOpened = remember { mutableStateOf(false) }
    val prioritiesScrollState = rememberScrollState()
    PriorityDialog(
        scrollState = prioritiesScrollState,
        isDialogOpened = isDialogOpened,
        fields = fields,
        draftPriority = draftPriority,
    )
    TicketCreateItem(
        title = "Приоритет",
        icon = com.example.core.R.drawable.baseline_priority_high_24
    ) {
        CustomSelectableText(
            label = draftPriority.value?.name,
            nullLabel = "Выбрать приоритет",
        ) { isDialogOpened.value = true }
    }
}

@Composable
internal fun OptionalFieldsTitleComponent() {
    CustomTitle(
        text = "Необязательные поля",
        fontSize = MaterialTheme.typography.h5.fontSize,
        modifier = Modifier.padding(top = 20.dp)
    )
}

@Composable
internal fun NameComponent(draft: DraftEntity) {
    val draftName = remember { mutableStateOf(draft.name) }
    TicketCreateItem(
        title = "Название",
        icon = com.example.core.R.drawable.baseline_title_24
    ) {
        CustomTextField(
            text = draftName,
            hint = "Ввести название"
        )
    }
}

@Composable
internal fun ExecutorComponent(draft: DraftEntity, fields: MutableState<TicketData?>) {
    val draftExecutor = remember { mutableStateOf(draft.executor) }
    val isDialogOpened = remember { mutableStateOf(false) }
    val executorsScrollState = rememberScrollState()
    ExecutorDialog(
        scrollState = executorsScrollState,
        isDialogOpened = isDialogOpened,
        fields = fields,
        draftExecutor = draftExecutor,
    )
    TicketCreateItem(
        title = "Исполнитель",
        icon = com.example.core.R.drawable.ic_baseline_person_24
    ) {
        CustomSelectableText(
            label = draftExecutor.value?.getFullName(),
            nullLabel = "Выбрать исполнителя"
        ) { isDialogOpened.value = true }
    }
}

@Composable
internal fun BrigadeComponent(draft: DraftEntity, fields: MutableState<TicketData?>) {
    val draftBrigade: MutableList<UserEntity?> = remember {
        draft.brigade?.toMutableStateList() ?: mutableStateListOf()
    }
    val isDialogOpened = remember { mutableStateOf(false) }
    val brigadeScrollState = rememberScrollState()
    BrigadeDialog(
        scrollState = brigadeScrollState,
        isDialogOpened = isDialogOpened,
        fields = fields,
        draftBrigade = draftBrigade
    )
    TicketCreateItem(
        title = "Бригада",
        icon = com.example.core.R.drawable.baseline_people_24
    ) {
        CustomChipRow(
            modifier = Modifier,
            addingChipTitle = "Добавить сотрудников",
            isDialogOpened = isDialogOpened
        ) {
            draftBrigade.forEach {
                CustomChip(title = it?.getFullName() ?: "") { draftBrigade.remove(it) }
            }
        }
    }
}

@Composable
internal fun PlaneDateComponent(draft: DraftEntity) {
    val draftPlaneDate = remember { mutableStateOf(draft.plane_date) }
    val isDialogOpened = remember { mutableStateOf(false) }
    CustomDatePicker(
        date = draftPlaneDate,
        isDialogOpened = isDialogOpened,
    )
    TicketCreateItem(
        title = "Плановая дата",
        icon = com.example.core.R.drawable.ic_baseline_calendar_month_24
    ) {
        CustomSelectableText(
            label = draftPlaneDate.value?.time.toString(),
            nullLabel = "Выбрать плановую дату"
        ) { isDialogOpened.value = true }
    }
}

@Composable
internal fun DescriptionComponent(draft: DraftEntity) {
    val draftDescription = remember { mutableStateOf(draft.description) }
    TicketCreateItem(
        title = "Описание",
        icon = com.example.core.R.drawable.baseline_text_format_24
    ) {
        CustomTextField(
            text = draftDescription,
            hint = "Ввести описание"
        )
    }
}

@Composable
internal fun AutomaticFieldsTitleComponent() {
    CustomTitle(
        text = "Автоматические поля",
        fontSize = MaterialTheme.typography.h5.fontSize,
        modifier = Modifier.padding(top = 20.dp)
    )
}

@Composable
internal fun StatusComponent(draft: DraftEntity) {
    val draftStatus = remember { mutableStateOf(draft.status) }
    TicketCreateItem(
        title = "Статус",
        icon = com.example.core.R.drawable.ic_baseline_playlist_add_check_24
    ) {
        CustomSelectableText(
            label = draftStatus.value?.name ?: "",
            nullLabel = "Выбрать статус"
        )
    }
}

@Composable
internal fun AuthorComponent(authParams: AuthParams) {
    TicketCreateItem(
        title = "Автор",
        icon = com.example.core.R.drawable.ic_baseline_person_24
    ) {
        CustomSelectableText(
            label = authParams.user?.getFullName(),
            nullLabel = "Автор не определен"
        )
    }
}

@Composable
private fun TicketCreateItem(title: String, icon: Int, item: @Composable () -> Unit) {
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