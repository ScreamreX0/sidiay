package com.example.home.ui.tickets_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.ScreenPreview
import com.example.domain.data_classes.params.FilteringParams
import com.example.domain.data_classes.params.SortBy
import com.example.domain.data_classes.params.SortingParams
import com.example.domain.data_classes.params.TicketData
import com.example.domain.enums.KindsEnum
import com.example.domain.enums.PrioritiesEnum
import com.example.domain.enums.ServicesEnum
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses
import com.example.home.ui.common.components.CustomDatePicker
import com.google.accompanist.flowlayout.FlowRow

@Composable
internal fun FilterDialog(
    onConfirmButton: () -> Unit = {},
    sortingParams: MutableState<SortingParams> = mutableStateOf(SortingParams()),
    filteringParams: MutableState<FilteringParams> = mutableStateOf(FilteringParams()),
    isDialogOpened: MutableState<Boolean> = mutableStateOf(true),
    ticketData: MutableState<TicketData?> = mutableStateOf(null),
) {
    if (!isDialogOpened.value) return

    Dialog(
        onDismissRequest = { isDialogOpened.value = false },
        content = {
            Surface(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(600.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    content = {
                        item {
                            //
                            // Title
                            Row {
                                Text(
                                    modifier = Modifier.padding(bottom = 16.dp, end = 8.dp),
                                    text = "Фильтрация и сортировка",
                                    fontSize = MaterialTheme.typography.h4.fontSize,
                                    fontWeight = FontWeight.Bold,
                                )
                            }

                            //
                            // Sorting
                            Row {
                                Text(
                                    modifier = Modifier.padding(bottom = 16.dp, end = 8.dp),
                                    text = "Сортировка",
                                    fontSize = MaterialTheme.typography.h4.fontSize,
                                )
                                Icon(
                                    modifier = Modifier.clickable {
                                        sortingParams.value = sortingParams.value.copy(
                                            sortBy = when (sortingParams.value.sortBy) {
                                                SortBy.ASCENDING -> {
                                                    SortBy.DESCENDING
                                                }

                                                else -> {
                                                    SortBy.ASCENDING
                                                }
                                            }
                                        )
                                    },
                                    painter = painterResource(
                                        if (sortingParams.value.sortBy == SortBy.ASCENDING) {
                                            com.example.core.R.drawable.baseline_keyboard_arrow_down_24
                                        } else {
                                            com.example.core.R.drawable.baseline_keyboard_arrow_up_24
                                        }
                                    ),
                                    contentDescription = "sorting ascending and descending",
                                    tint = MaterialTheme.colors.onBackground.copy(alpha = 0.65F)
                                )
                                Icon(
                                    modifier = Modifier.clickable {
                                        sortingParams.value = SortingParams()
                                    },
                                    painter = painterResource(com.example.core.R.drawable.baseline_delete_24),
                                    contentDescription = "clear sorting params",
                                    tint = MaterialTheme.colors.onBackground.copy(alpha = 0.65F)
                                )
                            }
                            SortingChipRow(
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    bottom = 8.dp
                                ),
                                sortingParams = sortingParams,
                            )
                            Divider(modifier = Modifier.padding(vertical = 8.dp))

                            //
                            // Filtering
                            Row {
                                Text(
                                    modifier = Modifier.padding(bottom = 8.dp, end = 8.dp),
                                    text = "Фильтрация",
                                    fontSize = MaterialTheme.typography.h4.fontSize,
                                )
                                Icon(
                                    modifier = Modifier.clickable {
                                        filteringParams.value = FilteringParams()
                                    },
                                    painter = painterResource(com.example.core.R.drawable.baseline_delete_24),
                                    contentDescription = "clear filtering params",
                                    tint = MaterialTheme.colors.onBackground.copy(alpha = 0.65F)
                                )
                            }
                            FiltersComponent(
                                filteringParams = filteringParams,
                                ticketData = ticketData
                            )

                            //
                            // Bottom buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .clickable { isDialogOpened.value = false },
                                    text = "Отмена",
                                    fontSize = MaterialTheme.typography.h4.fontSize,
                                )
                                Text(
                                    modifier = Modifier.clickable { onConfirmButton() },
                                    text = "ОК",
                                    fontSize = MaterialTheme.typography.h4.fontSize,
                                )
                            }
                        }
                    }
                )
            }
        }
    )
}

@Composable
private fun FiltersComponent(
    filteringParams: MutableState<FilteringParams> = mutableStateOf(FilteringParams()),
    ticketData: MutableState<TicketData?> = mutableStateOf(null)
) {
    // priorities
    DropdownCheckboxMenu(
        items = PrioritiesEnum.values().toList(),
        label = "По приоритетам",
        onSelectionChange = { filteringParams.value = filteringParams.value.copy(priority = it) },
        selectedItems = filteringParams.value.priority,
        itemText = { it.label }
    )

    // services
    DropdownCheckboxMenu(
        items = ServicesEnum.values().toList(),
        label = "По сервисам",
        onSelectionChange = { filteringParams.value = filteringParams.value.copy(services = it) },
        selectedItems = filteringParams.value.services,
        itemText = { it.label }
    )

    // kinds
    DropdownCheckboxMenu(
        items = KindsEnum.values().toList(),
        label = "По видам",
        onSelectionChange = { filteringParams.value = filteringParams.value.copy(kinds = it) },
        selectedItems = filteringParams.value.kinds,
        itemText = { it.label }
    )

    // statuses
    DropdownCheckboxMenu(
        items = TicketStatuses.values().toList(),
        label = "По статусам",
        onSelectionChange = { filteringParams.value = filteringParams.value.copy(status = it) },
        selectedItems = filteringParams.value.status,
        itemText = { it.title }
    )
}

@Composable
private fun DatePicker(
    isDialogOpened: MutableState<Boolean> = mutableStateOf(false),
    selectedDate: String? = null,
    onConfirmButton: (String?) -> Unit,
    label: String = "label",
) {
    // TODO("Implement")
    Text(
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { isDialogOpened.value = true }
            .padding(start = 16.dp),
        text = label,
    )

    if (isDialogOpened.value) {
        CustomDatePicker(
            date = selectedDate ?: "[Выберите дату для фильтрации]",
            isDialogOpened = isDialogOpened,
            onConfirm = { onConfirmButton(it) }
        )
    }
}

@Composable
private fun SortingChipRow(
    modifier: Modifier = Modifier,
    sortingParams: MutableState<SortingParams> = mutableStateOf(SortingParams()),
) {
    FlowRow(
        modifier = modifier,
        mainAxisSpacing = 10.dp,
        crossAxisSpacing = 10.dp,
    ) {
        SortingChip(TicketFieldsEnum.ID, sortingParams, "По номеру")
        SortingChip(TicketFieldsEnum.TICKET_NAME, sortingParams, "По названию")
        SortingChip(TicketFieldsEnum.SERVICE, sortingParams, "По сервису")
        SortingChip(TicketFieldsEnum.PLANE_DATE, sortingParams, "По плановой дате")
        SortingChip(TicketFieldsEnum.STATUS, sortingParams, "По статусу")
        SortingChip(TicketFieldsEnum.PRIORITY, sortingParams, "По приоритету")
        SortingChip(TicketFieldsEnum.AUTHOR, sortingParams, "По автору")
        SortingChip(TicketFieldsEnum.KIND, sortingParams, "По виду")
        SortingChip(TicketFieldsEnum.CLOSING_DATE, sortingParams, "По дате закрытия")
        SortingChip(TicketFieldsEnum.CREATION_DATE, sortingParams, "По дате создания")
        SortingChip(TicketFieldsEnum.DESCRIPTION_OF_WORK, sortingParams, "По описанию")
    }
}

@Composable
private fun SortingChip(
    ticketField: TicketFieldsEnum,
    sortingParams: MutableState<SortingParams>,
    title: String
) {
    val currentChipSelected = sortingParams.value.field == ticketField

    Box(
        modifier = Modifier
            .height(35.dp)
            .clip(CircleShape)
            .background(
                if (currentChipSelected) {
                    MaterialTheme.colors.onBackground
                } else {
                    MaterialTheme.colors.onBackground.copy(alpha = 0.65F)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    if (currentChipSelected) {
                        sortingParams.value = SortingParams()
                    } else {
                        sortingParams.value = sortingParams.value.copy(field = ticketField)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                color = MaterialTheme.colors.onPrimary,
            )
        }
    }
}

@Composable
private fun <T> DropdownCheckboxMenu(
    items: List<T>,
    label: String = "label",
    onSelectionChange: (List<T>) -> Unit = {},
    selectedItems: List<T> = emptyList(),
    itemText: (T) -> String = { "itemText" }
) {
    val expanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(start = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = { expanded.value = true })
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) { Text(text = label) }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            items.forEach { item ->
                val checked = selectedItems.contains(item)
                DropdownMenuItem(
                    onClick = {
                        onSelectionChange(
                            if (checked) {
                                selectedItems - item
                            } else {
                                selectedItems + item
                            }
                        )
                    }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = null
                        )
                        Text(text = itemText(item))
                    }
                }
            }
        }
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme(isSystemInDarkTheme()) { FilterDialog() }
}