package com.example.home.ui.common.impl.clickable_texts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.PrioritiesEnum
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.components.ListElement
import com.example.home.ui.common.interfaces.ICustomClickableText

class PriorityClickableText(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val ticketData: List<PrioritiesEnum>? = PrioritiesEnum.values().toList(),
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.PRIORITY,
) : ICustomClickableText<PrioritiesEnum, PrioritiesEnum> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            dialogTitle = "Выберите приоритет",
            ticketData = ticketData,
            predicate = { it, searchTextState -> it.label.contains(searchTextState.text, true) },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.label) {
                    ticket.value = ticket.value.copy(priority = it.value)
                    isDialogOpened.value = false
                }
            },
            title = "Приоритет",
            label = PrioritiesEnum.getByValue(ticket.value.priority)?.label ?: "[Выбрать приоритет]",
            icon = R.drawable.baseline_signal_cellular_alt_24,
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}