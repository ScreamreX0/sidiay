package com.example.home.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.components.CustomTextField

interface ICustomTextField : ITicketField {
    override val field: TicketFieldsEnum
    override val ticketFieldsParams: MutableState<TicketFieldParams>

    @Composable
    fun Content(ticketRestrictions: TicketRestriction) {
        this.ticketFieldsParams.value = getTicketFieldsParams(field, ticketRestrictions)
    }

    @Composable
    fun Component(
        title: String,
        icon: Int,
        text: String?,
        onValueChange: (newValue: String) -> Unit,
        textFieldHint: String,
        ticketFieldsParams: TicketFieldParams
    ) {
        TicketFieldComponent(
            title = title,
            icon = icon,
            item = {
                CustomTextField(
                    text = text,
                    onValueChange = onValueChange,
                    hint = textFieldHint,
                    isClickable = ticketFieldsParams.isClickable
                )
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}