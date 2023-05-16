package com.example.home.ui.common.interfaces

import androidx.compose.runtime.Composable
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.home.ui.common.components.CustomText

internal interface INonSelectableText<E> : ITicketField<E> {
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