package com.example.home.ui.common.impl.chip_rows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.core.R
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.EquipmentEntity
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.TransportEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.home.ui.common.ICustomChipRow
import com.example.home.ui.common.components.CustomChip
import com.example.home.ui.common.components.ListElement

class EquipmentChipRow(
    override val field: List<EquipmentEntity>?,
    override val ticketData: List<EquipmentEntity>?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.EQUIPMENT,
) : ICustomChipRow<EquipmentEntity, List<EquipmentEntity>> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            dialogTitle = "Выберите оборудование",
            ticketData = ticketData,
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
                        if (ticketFieldsParams.isClickable) {
                            ticket.value = ticket.value.copy(
                                equipment = Helper.removeFromList(ticket.value.equipment, it)
                            )
                        }
                    }
                }
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}