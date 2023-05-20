package com.example.home.ui.common.impl.text_fields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomTextField

class AssessedValueTextField(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.ASSESSED_VALUE,
) : ICustomTextField<Float> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            title = "Оценочная стоимость",
            icon = R.drawable.baseline_fast_forward_24,
            text = ticket.value.assessed_value?.toString() ?: "",
            onValueChange = {
                it.toFloatOrNull()?.let { itNumber ->
                    ticket.value = ticket.value.copy(assessed_value = itNumber)
                }
            },
            textFieldHint = ticket.value.completed_work ?: "[Ввести оценочную стоимость]",
            ticketFieldsParams = ticketFieldsParams,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}