package com.example.home.ui.common.impl.date_pickers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ClosingDatePicker(
    override val ticketFieldsParams: MutableState<TicketFieldParams> = mutableStateOf(TicketFieldParams()),
    override val field: TicketFieldsEnum = TicketFieldsEnum.CLOSING_DATE,
    override val ticket: MutableState<TicketEntity>,
    override val ticketRestrictions: TicketRestriction,
    override val isValueNull: Boolean
) : ICustomDatePicker {
    @Composable
    fun Content() {
        super.init(this, ticketRestrictions, isValueNull)

        Component(
            date = ticket.value.closing_date ?: LocalDate.now().toString(),
            onConfirmDatePicker = { ticket.value = ticket.value.copy(closing_date = it) },
            title = "Дата закрытия",
            icon = R.drawable.ic_baseline_calendar_month_24,
            datePickerTitle = ticket.value.closing_date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                ?: "[Выбрать дату закрытия]",
            ticketFieldsParams = ticketFieldsParams.value,
        )
    }
}