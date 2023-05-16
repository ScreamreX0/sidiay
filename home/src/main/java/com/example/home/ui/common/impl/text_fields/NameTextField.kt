package com.example.home.ui.common.impl.text_fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomTextField

class NameTextField(
    override val field: String?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.TICKET_NAME,
) : ICustomTextField<String> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Название",
            icon = R.drawable.baseline_title_24,
            text = ticket.value.ticket_name,
            onValueChange = { ticket.value = ticket.value.copy(ticket_name = it) },
            textFieldHint = "Ввести название",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}