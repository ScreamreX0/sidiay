package com.example.home.ui.common.impl.date_pickers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomDatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ClosingDatePicker(
    override val field: String?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.CLOSING_DATE,
) : ICustomDatePicker<String> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            date = ticket.value.closing_date ?: LocalDate.now().toString(),
            onConfirmDatePicker = { ticket.value = ticket.value.copy(closing_date = it) },
            title = "Дата закрытия",
            icon = R.drawable.ic_baseline_calendar_month_24,
            datePickerTitle = ticket.value.closing_date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                ?: "[Выбрать дату закрытия]",
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}