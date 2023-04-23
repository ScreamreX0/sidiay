package com.example.home.ui.common.impl.clickable_texts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomClickableText
import com.example.home.ui.common.components.ListElement

class PriorityClickableText(
    override val ticketFieldsParams: MutableState<TicketFieldParams> = mutableStateOf(TicketFieldParams()),
    override val field: TicketFieldsEnum = TicketFieldsEnum.PRIORITY,
    override val ticketData: MutableState<TicketData?>,
    override val ticket: MutableState<TicketEntity>,
    override val ticketRestrictions: TicketRestriction,
    override val isValueNull: Boolean
) : ICustomClickableText {
    @Composable
    fun Content() {
        super.init(this, ticketRestrictions, isValueNull)
        if (!ticketFieldsParams.value.isVisible) return

        Component(
            dialogTitle = "Выберите приоритет",
            ticketData = ticketData.value?.priorities,
            predicate = { it, searchTextState ->
                it.name?.contains(searchTextState.text, true) ?: false
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.name ?: "") {
                    ticket.value = ticket.value.copy(priority = it)
                    isDialogOpened.value = false
                }
            },
            title = "Приоритет",
            label = ticket.value.priority?.name ?: "[Выбрать приоритет]",
            icon = R.drawable.baseline_priority_high_24,
            ticketFieldsParams = ticketFieldsParams.value,
        )
    }
}