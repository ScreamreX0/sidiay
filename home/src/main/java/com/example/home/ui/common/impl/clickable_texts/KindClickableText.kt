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

class KindClickableText(
    override val ticketFieldsParams: MutableState<TicketFieldParams> = mutableStateOf(TicketFieldParams()),
    override val field: TicketFieldsEnum = TicketFieldsEnum.KIND,
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
            dialogTitle = "Выберите вид",
            ticketData = ticketData.value?.kinds,
            predicate = { it, searchTextState ->
                it.name?.contains(searchTextState.text, true) ?: false
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.name ?: "") {
                    ticket.value = ticket.value.copy(kind = it)
                    isDialogOpened.value = false
                }
            },
            title = "Вид",
            label = ticket.value.kind?.name ?: "[Выбрать вид]",
            icon = R.drawable.baseline_format_list_bulleted_24,
            ticketFieldsParams = ticketFieldsParams.value,
        )
    }
}