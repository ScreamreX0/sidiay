package com.example.home.ui.common.impl.text_fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomTextField

class DescriptionTextField(
    override val field: String?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.DESCRIPTION,
) : ICustomTextField<String> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Описание",
            icon = R.drawable.baseline_text_format_24,
            text = ticket.value.description,
            onValueChange = { ticket.value = ticket.value.copy(description = it) },
            textFieldHint = ticket.value.description ?: "Ввести описание",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}