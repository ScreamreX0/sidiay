package com.example.home.ui.common.impl.text_fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomTextField

class ReasonForSuspensionTextField(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.REASON_FOR_SUSPENSION,
) : ICustomTextField<String> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Причина приостановки заявки",
            icon = R.drawable.baseline_description_24,
            text = ticket.value.reason_for_suspension,
            onValueChange = { ticket.value = ticket.value.copy(reason_for_suspension = it) },
            textFieldHint = ticket.value.reason_for_suspension ?: "Ввести причину",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}