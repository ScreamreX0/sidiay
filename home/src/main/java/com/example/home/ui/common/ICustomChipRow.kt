package com.example.home.ui.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.home.ui.common.components.CustomChipRow
import com.example.home.ui.common.components.CustomDialog

internal interface ICustomChipRow<T, E> : ITicketField<E> {
    val ticketData: List<T>?
    @Composable
    fun <T> Component(
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
        TicketFieldComponent(
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
}