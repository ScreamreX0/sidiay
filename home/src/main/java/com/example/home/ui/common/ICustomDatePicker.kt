package com.example.home.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.components.CustomDatePicker
import com.example.home.ui.common.components.CustomText

internal interface ICustomDatePicker<E> : ITicketField<E> {
    @Composable
    fun Component(
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
        TicketFieldComponent(
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
}