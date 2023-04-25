package com.example.home.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.domain.data_classes.entities.PriorityEntity
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses
import com.example.home.ui.common.impl.chip_rows.BrigadeChipRow
import com.example.home.ui.common.impl.chip_rows.EquipmentChipRow
import com.example.home.ui.common.impl.chip_rows.FacilitiesChipRow
import com.example.home.ui.common.impl.chip_rows.TransportChipRow
import com.example.home.ui.common.impl.clickable_texts.ExecutorClickableText
import com.example.home.ui.common.impl.clickable_texts.KindClickableText
import com.example.home.ui.common.impl.clickable_texts.PriorityClickableText
import com.example.home.ui.common.impl.clickable_texts.ServiceClickableText
import com.example.home.ui.common.impl.date_pickers.PlaneDatePicker
import com.example.home.ui.common.impl.drop_down_menu.StatusDropDownMenu
import com.example.home.ui.common.impl.other.AuthorNonSelectableText
import com.example.home.ui.common.impl.other.CreationDateNonSelectableText
import com.example.home.ui.common.impl.text_fields.CompletedWorkTextField
import com.example.home.ui.common.impl.text_fields.DescriptionTextField
import com.example.home.ui.common.impl.text_fields.ImprovementReasonTextField
import com.example.home.ui.common.impl.text_fields.NameTextField

class TicketFieldsFactory(
    private val ticketData: TicketData?,
    private val ticket: MutableState<TicketEntity>,
    private val ticketRestriction: MutableState<TicketRestriction>,
    private val updateRestrictions: () -> Unit,
    private val selectedTicketStatus: MutableState<TicketStatuses>,
) {
    @Composable
    fun <E> GetField(fieldEnum: TicketFieldsEnum, field: E) {
        val params = getParams(fieldEnum = fieldEnum, ticketRestriction = ticketRestriction.value, field)

        return when(fieldEnum) {
            TicketFieldsEnum.FACILITIES -> FacilitiesChipRow(ticket.value.facilities, ticketData?.facilities, params, ticket).Content()
            TicketFieldsEnum.EQUIPMENT -> EquipmentChipRow(ticket.value.equipment, ticketData?.equipment, params, ticket).Content()
            TicketFieldsEnum.TRANSPORT -> TransportChipRow(ticket.value.transport, ticketData?.transport, params, ticket).Content()
            TicketFieldsEnum.BRIGADE -> BrigadeChipRow(ticket.value.brigade, ticketData?.users, params, ticket).Content()
            TicketFieldsEnum.NAME -> NameTextField(ticket.value.name, params, ticket).Content()
            TicketFieldsEnum.DESCRIPTION -> DescriptionTextField(ticket.value.description, params, ticket).Content()
            TicketFieldsEnum.SERVICE -> ServiceClickableText(ticket.value.service, ticketData?.services, params, ticket).Content()
            TicketFieldsEnum.KIND -> KindClickableText(ticket.value.kind, ticketData?.kinds, params, ticket).Content()
            TicketFieldsEnum.PRIORITY -> PriorityClickableText(ticket.value.priority, ticketData?.priorities, params, ticket).Content()
            TicketFieldsEnum.EXECUTOR -> ExecutorClickableText(ticket.value.executor, ticketData?.users, params, ticket).Content()
            TicketFieldsEnum.COMPLETED_WORK -> CompletedWorkTextField(ticket.value.completed_work, params, ticket).Content()
            TicketFieldsEnum.PLANE_DATE -> PlaneDatePicker(ticket.value.plane_date, params, ticket).Content()
            TicketFieldsEnum.CLOSING_DATE -> PlaneDatePicker(ticket.value.plane_date, params, ticket).Content()
            TicketFieldsEnum.CREATION_DATE -> CreationDateNonSelectableText(ticket.value.plane_date, params, ticket).Content()
            TicketFieldsEnum.AUTHOR -> AuthorNonSelectableText(ticket.value.author, params, ticket).Content()
            TicketFieldsEnum.STATUS -> StatusDropDownMenu(TicketStatuses.get(ticket.value.status), ticketRestriction.value.availableStatuses, params, updateRestrictions, ticketRestriction.value, selectedTicketStatus).Content()
            TicketFieldsEnum.IMPROVEMENT_REASON -> ImprovementReasonTextField(ticket.value.improvement_reason, params, ticket).Content()
            else -> {}
        }
    }

    private fun <E> getParams(
        fieldEnum: TicketFieldsEnum,
        ticketRestriction: TicketRestriction,
        field: E
    ): TicketFieldParams {
        val ticketFieldInRequiredFields = fieldEnum in ticketRestriction.requiredFields
        val ticketFieldInAllowedFields = fieldEnum in ticketRestriction.allowedFields

        return TicketFieldParams(
            starred = ticketFieldInRequiredFields,
            isClickable = ticketFieldInRequiredFields || ticketFieldInAllowedFields,
            isVisible = field != null || ticketFieldInRequiredFields || ticketFieldInAllowedFields,
        )
    }
}