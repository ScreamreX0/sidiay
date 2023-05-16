package com.example.home.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses
import com.example.home.ui.common.impl.chip_rows.EquipmentChipRow
import com.example.home.ui.common.impl.chip_rows.FacilitiesChipRow
import com.example.home.ui.common.impl.chip_rows.TransportChipRow
import com.example.home.ui.common.impl.clickable_texts.KindClickableText
import com.example.home.ui.common.impl.clickable_texts.PriorityClickableText
import com.example.home.ui.common.impl.clickable_texts.ServiceClickableText
import com.example.home.ui.common.impl.date_pickers.ClosingDatePicker
import com.example.home.ui.common.impl.date_pickers.PlaneDatePicker
import com.example.home.ui.common.impl.drop_down_menu.StatusDropDownMenu
import com.example.home.ui.common.impl.other.AuthorText
import com.example.home.ui.common.impl.other.CreationDateText
import com.example.home.ui.common.impl.text_fields.CompletedWorkTextField
import com.example.home.ui.common.impl.text_fields.DescriptionTextField
import com.example.home.ui.common.impl.text_fields.ImprovementCommentTextField
import com.example.home.ui.common.impl.text_fields.NameTextField

class TicketFieldsFactory(
    private val ticketData: TicketData?,
    private val ticket: MutableState<TicketEntity>,
    private val ticketRestriction: MutableState<TicketRestriction>,
    private val updateRestrictions: () -> Unit,
    private val selectedTicketStatus: MutableState<TicketStatuses>,
) {
    @Composable
    fun <T> GetField(fieldEnum: TicketFieldsEnum, field: T) {
        val params = getParams(fieldEnum = fieldEnum, ticketRestriction = ticketRestriction.value, field)

        return when(fieldEnum) {
            TicketFieldsEnum.STATUS -> StatusDropDownMenu(TicketStatuses.get(ticket.value.status), ticketRestriction.value.availableStatuses, params, updateRestrictions, ticketRestriction.value, selectedTicketStatus).Content()
            TicketFieldsEnum.AUTHOR -> AuthorText(ticket.value.author, params, ticket).Content()
            TicketFieldsEnum.FIELD -> TODO()
            TicketFieldsEnum.DISPATCHER -> TODO()
            TicketFieldsEnum.EXECUTORS_NOMINATOR -> TODO()
            TicketFieldsEnum.QUALITY_CONTROLLERS_NOMINATOR -> TODO()
            TicketFieldsEnum.CREATION_DATE -> CreationDateText(ticket.value.plane_date, params, ticket).Content()
            TicketFieldsEnum.TICKET_NAME -> NameTextField(ticket.value.ticket_name, params, ticket).Content()
            TicketFieldsEnum.DESCRIPTION_OF_WORK -> DescriptionTextField(ticket.value.description_of_work, params, ticket).Content()
            TicketFieldsEnum.KIND -> KindClickableText(ticket.value.kind, ticketData?.kinds, params, ticket).Content()
            TicketFieldsEnum.SERVICE -> ServiceClickableText(ticket.value.service, ticketData?.services, params, ticket).Content()
            TicketFieldsEnum.FACILITIES -> FacilitiesChipRow(ticket.value.facilities, ticketData?.facilities, params, ticket).Content()
            TicketFieldsEnum.EQUIPMENT -> EquipmentChipRow(ticket.value.equipment, ticketData?.equipment, params, ticket).Content()
            TicketFieldsEnum.TRANSPORT -> TransportChipRow(ticket.value.transport, ticketData?.transport, params, ticket).Content()
            TicketFieldsEnum.PRIORITY -> PriorityClickableText(ticket.value.priority, ticketData?.priorities, params, ticket).Content()
            TicketFieldsEnum.ASSESSED_VALUE -> TODO()
            TicketFieldsEnum.ASSESSED_VALUE_DESCRIPTION -> TODO()
            TicketFieldsEnum.REASON_FOR_CANCELLATION -> TODO()
            TicketFieldsEnum.REASON_FOR_REJECTION -> TODO()
            TicketFieldsEnum.EXECUTORS -> TODO()
            TicketFieldsEnum.PLANE_DATE -> PlaneDatePicker(ticket.value.plane_date, params, ticket).Content()
            TicketFieldsEnum.REASON_FOR_SUSPENSION -> TODO()
            TicketFieldsEnum.COMPLETED_WORK -> CompletedWorkTextField(ticket.value.completed_work, params, ticket).Content()
            TicketFieldsEnum.QUALITY_CONTROLLERS -> TODO()
            TicketFieldsEnum.IMPROVEMENT_COMMENT -> ImprovementCommentTextField(ticket.value.improvement_comment, params, ticket).Content()
            TicketFieldsEnum.CLOSING_DATE -> ClosingDatePicker(ticket.value.plane_date, params, ticket).Content()
            else -> {}
        }
    }

    private fun <T> getParams(
        fieldEnum: TicketFieldsEnum,
        ticketRestriction: TicketRestriction,
        field: T
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