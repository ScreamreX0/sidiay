package com.example.home.ui.common.impl.clickable_texts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ServicesEnum
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomClickableText
import com.example.home.ui.common.components.ListElement

class ServiceClickableText(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val ticketData: List<ServicesEnum>? = ServicesEnum.values().toList(),
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.SERVICE,
) : ICustomClickableText<ServicesEnum, ServicesEnum> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            dialogTitle = "Выберите сервис",
            ticketData = ticketData,
            predicate = { it, searchTextState -> it.label.contains(searchTextState.text, true) },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.label) {
                    ticket.value = ticket.value.copy(service = it.value)
                    isDialogOpened.value = false
                }
            },
            title = "Сервисы",
            label = ticket.value.service?.let { ServicesEnum.getByValue(it) }?.label ?: "[Выбрать сервис]",
            icon = R.drawable.baseline_format_list_bulleted_24,
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}