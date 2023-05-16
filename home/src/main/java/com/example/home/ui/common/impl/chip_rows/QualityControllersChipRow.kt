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

class QualityControllersChipRow(
    override val ticketData: List<UserEntity>?,
    override val ticketFieldsParams: TicketFieldParams,
    private val ticket: MutableState<TicketEntity>,
    override val fieldEnum: TicketFieldsEnum = TicketFieldsEnum.QUALITY_CONTROLLERS,
) : ICustomChipRow<UserEntity, List<UserEntity>> {
    @Composable
    fun Content() {
        if (!ticketFieldsParams.isVisible) return

        Component(
            dialogTitle = "Выберите сотрудников",
            ticketData = ticketData,
            predicate = { it, searchTextState ->
                it.employee?.firstname?.contains(searchTextState.text, true) ?: false
                        || it.employee?.name?.contains(searchTextState.text, true) ?: false
            },
            listItem = { it, isDialogOpened ->
                ListElement(title = it.getFullName()) {
                    if (ticket.value.quality_controllers?.contains(it) != true) {
                        ticket.value = ticket.value.copy(
                            quality_controllers = Helper.addToList(ticket.value.quality_controllers, it)
                        )
                    }
                    isDialogOpened.value = false
                }
            },
            title = "Сотрудники по контролю качества",
            icon = R.drawable.baseline_computer_24,
            addingChipTitle = "Добавить сотрудников",
            chips = {
                ticket.value.quality_controllers?.forEach {
                    CustomChip(title = it.getFullName()) {
                        if (ticketFieldsParams.isClickable) {
                            ticket.value = ticket.value.copy(
                                quality_controllers = Helper.removeFromList(ticket.value.quality_controllers, it)
                            )
                        }
                    }
                }
            },
            ticketFieldsParams = ticketFieldsParams,
        )
    }
}