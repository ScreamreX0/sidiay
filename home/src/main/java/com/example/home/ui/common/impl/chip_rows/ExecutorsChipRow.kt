package com.example.home.ui.common.impl.chip_rows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.core.R
import com.example.core.utils.Helper
import com.example.domain.data_classes.entities.EquipmentEntity
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.home.ui.common.interfaces.ICustomChipRow
import com.example.home.ui.common.components.CustomChip
import com.example.home.ui.common.components.ListElement

class ExecutorsChipRow(
    override val ticketData: List<UserEntity>?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.EXECUTORS,
) : ICustomChipRow<UserEntity, List<UserEntity>> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            dialogTitle = "Выберите исполнителей",
            ticketData = ticketData,
            predicate = { it, searchTextState ->
                it.employee?.firstname?.contains(searchTextState.text, true) ?: false
                        || it.employee?.name?.contains(searchTextState.text, true) ?: false
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.getFullName()) {
                    if (ticket.value.executors?.contains(it) != true) {
                        ticket.value = ticket.value.copy(
                            executors = Helper.addToList(ticket.value.executors, it)
                        )
                    }
                    isDialogOpened.value = false
                }
            },
            title = "Исполнители",
            icon = R.drawable.baseline_computer_24,
            addingChipTitle = "Добавить исполнителей",
            chips = {
                ticket.value.executors?.forEach {
                    CustomChip(title = it.getFullName()) {
                        if (ticketFieldsParams.isClickable) {
                            ticket.value = ticket.value.copy(
                                executors = Helper.removeFromList(ticket.value.executors, it)
                            )
                        }
                    }
                }
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}