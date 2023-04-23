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

class ImprovementReasonTextField(
    override val ticketFieldsParams: MutableState<TicketFieldParams> = mutableStateOf(TicketFieldParams()),
    override val field: TicketFieldsEnum = TicketFieldsEnum.IMPROVEMENT_REASON,
    override val ticket: MutableState<TicketEntity>,
    override val ticketRestrictions: TicketRestriction,
    override val isValueNull: Boolean,
) : ICustomTextField {
    @Composable
    fun Content() {
        super.init(this, ticketRestrictions, isValueNull)

        Component(
            title = "Причина доработки",
            icon = R.drawable.baseline_text_format_24,
            text = ticket.value.improvement_reason,
            onValueChange = { ticket.value = ticket.value.copy(improvement_reason = it) },
            textFieldHint = ticket.value.improvement_reason ?: "Ввести причину доработки",
            ticketFieldsParams = ticketFieldsParams.value,
        )
    }
}