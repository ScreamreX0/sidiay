package com.example.home.ui.common.impl.clickable_texts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.KindsEnum
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.components.ListElement
import com.example.home.ui.common.interfaces.ICustomClickableText

class KindClickableText(
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val ticketData: List<KindsEnum>? = KindsEnum.values().toList(),
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.KIND,
) : ICustomClickableText<KindsEnum, KindsEnum> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            dialogTitle = "Выберите вид",
            ticketData = ticketData,
            predicate = { it, searchTextState ->
                it.label.contains(searchTextState.text, true)
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.label) {
                    ticket.value = ticket.value.copy(kind = it.value)
                    isDialogOpened.value = false
                }
            },
            title = "Вид",
            label = KindsEnum.getByValue(ticket.value.kind)?.label ?: "[Выбрать вид]",
            icon = R.drawable.baseline_format_list_bulleted_24,
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}