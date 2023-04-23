package com.example.home.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.components.CustomText

internal interface INonSelectableText : ITicketField {
    @Composable
    fun Component(
        title: String,
        icon: Int,
        label: String,
        ticketFieldsParams: TicketFieldParams
    ) {
        TicketFieldComponent(
            title = title,
            icon = icon,
            item = { CustomText(label = label) },
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}