package com.example.home.ui.tickets_list.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.theme.AppTheme
import com.example.core.utils.ScreenPreview
import com.example.domain.data_classes.params.FilteringParams
import com.example.domain.enums.TicketFieldsEnum
import com.google.accompanist.flowlayout.FlowRow

@Composable
internal fun FilterDialog(
    onConfirmButton: () -> Unit = {},
    sortingParams: MutableState<TicketFieldsEnum?> = mutableStateOf(null),
    filteringParams: MutableState<FilteringParams?> = mutableStateOf(null),
    isDialogOpened: MutableState<Boolean> = mutableStateOf(true),
    scrollState: ScrollState = rememberScrollState()
) {
    if (!isDialogOpened.value) return

    AlertDialog(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .height(300.dp),
        onDismissRequest = { /* Handle dialog dismiss */ },
        title = { Text(text = "Фильтрация и сортировка") },
        confirmButton = {
            Text(
                modifier = Modifier.clickable { onConfirmButton() },
                text = "OK",
                fontSize = MaterialTheme.typography.h3.fontSize
            )
        },
        dismissButton = {
            Text(text = "Cancel", fontSize = MaterialTheme.typography.h3.fontSize)
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .verticalScroll(scrollState)
            ) {
                // Sorting section
                Text(modifier = Modifier.padding(bottom = 5.dp), text = "Сортировка")
                SortingChipRow { sortingParams.value = it }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Filtering section
                Text(text = "Фильтрация")
                // Add your filtering components here
            }
        }
    )
}

@Composable
private fun SortingChipRow(onChipClicked: (TicketFieldsEnum) -> Unit) {
    FlowRow(
        modifier = Modifier,
        mainAxisSpacing = 10.dp,
        crossAxisSpacing = 10.dp,
    ) {
        SortingChip(ticketField = TicketFieldsEnum.ID, onChipClicked = onChipClicked, title = "По номеру")
        SortingChip(ticketField = TicketFieldsEnum.NAME, onChipClicked = onChipClicked, title = "По названию")
        SortingChip(ticketField = TicketFieldsEnum.SERVICE, onChipClicked = onChipClicked, title = "По сервису")
        SortingChip(ticketField = TicketFieldsEnum.EXECUTOR, onChipClicked = onChipClicked, title = "По исполнителю")
        SortingChip(ticketField = TicketFieldsEnum.PLANE_DATE, onChipClicked = onChipClicked, title = "По плановой дате")
        SortingChip(ticketField = TicketFieldsEnum.STATUS, onChipClicked = onChipClicked, title = "По статусу")
        SortingChip(ticketField = TicketFieldsEnum.PRIORITY, onChipClicked = onChipClicked, title = "По приоритету")
        SortingChip(ticketField = TicketFieldsEnum.AUTHOR, onChipClicked = onChipClicked, title = "По автору")
        SortingChip(ticketField = TicketFieldsEnum.KIND, onChipClicked = onChipClicked, title = "По виду")
        SortingChip(ticketField = TicketFieldsEnum.CLOSING_DATE, onChipClicked = onChipClicked, title = "По дате закрытия")
        SortingChip(ticketField = TicketFieldsEnum.CREATION_DATE, onChipClicked = onChipClicked, title = "По дате создания")
        SortingChip(ticketField = TicketFieldsEnum.DESCRIPTION, onChipClicked = onChipClicked, title = "По описанию")
    }
}

@Composable
private fun SortingChip(
    onChipClicked: (TicketFieldsEnum) -> Unit,
    ticketField: TicketFieldsEnum,
    title: String
) {
    Box(
        modifier = Modifier
            .height(35.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.onBackground),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { onChipClicked(ticketField) },
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


@ScreenPreview
@Composable
private fun Preview() {
    AppTheme(isSystemInDarkTheme()) { FilterDialog() }
}