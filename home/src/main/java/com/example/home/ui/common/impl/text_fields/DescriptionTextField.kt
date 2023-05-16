package com.example.home.ui.common.impl.text_fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomTextField

class DescriptionTextField(
    override val field: String?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.DESCRIPTION_OF_WORK,
) : ICustomTextField<String> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Описание",
            icon = R.drawable.baseline_description_24,
            text = ticket.value.description_of_work,
            onValueChange = { ticket.value = ticket.value.copy(description_of_work = it) },
            textFieldHint = ticket.value.description_of_work ?: "Ввести описание",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}