package com.example.home.ui.common.impl.chip_rows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomChipRow

class EquipmentChipRow(
    override val ticketFieldsParams: MutableState<TicketFieldParams> = mutableStateOf(TicketFieldParams()),
    override val field: TicketFieldsEnum = TicketFieldsEnum.EQUIPMENT,
    override val ticket: MutableState<TicketEntity>,
    override val ticketData: MutableState<TicketData?>,
    override val ticketRestrictions: TicketRestriction,
    override val isValueNull: Boolean
) : ICustomChipRow {
    @Composable
    fun Content() {
        super.init(this, ticketRestrictions, isValueNull)
        if (!ticketFieldsParams.value.isVisible) return

        Component(
            dialogTitle = "Выберите оборудование",
            ticketData = ticketData.value?.equipment,
            predicate = { it, searchTextState ->
                it.name?.contains(searchTextState.text, true) ?: false
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.name ?: "") {
                    if (ticket.value.equipment?.contains(it) != true) {
                        ticket.value = ticket.value.copy(
                            equipment = Helper.addToList(ticket.value.equipment, it)
                        )
                    }
                    isDialogOpened.value = false
                }
            },
            title = "Оборудование",
            icon = R.drawable.baseline_computer_24,
            addingChipTitle = "Добавить оборудование",
            chips = {
                ticket.value.equipment?.forEach {
                    CustomChip(title = it.name ?: "") {
                        ticket.value = ticket.value.copy(
                            equipment = Helper.removeFromList(ticket.value.equipment, it)
                        )
                    }
                }
            },
            ticketFieldsParams = ticketFieldsParams.value,
        )
    }
}