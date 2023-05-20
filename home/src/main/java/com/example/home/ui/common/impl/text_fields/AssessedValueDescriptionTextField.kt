package com.example.home.ui.common.impl.text_fields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomTextField

class AssessedValueDescriptionTextField(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.ASSESSED_VALUE_DESCRIPTION,
) : ICustomTextField<String> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Описание оценочной стоимости",
            icon = R.drawable.baseline_description_24,
            text = ticket.value.assessed_value_description,
            onValueChange = { ticket.value = ticket.value.copy(assessed_value_description = it) },
            textFieldHint = ticket.value.assessed_value_description ?: "Ввести описание оценочной стоимости",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}