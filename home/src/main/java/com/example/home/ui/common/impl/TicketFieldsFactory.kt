package com.example.home.ui.common.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.domain.data_classes.entities.TicketEntity
import com.example.domain.data_classes.params.TicketData
import com.example.domain.data_classes.params.TicketFieldParams
import com.example.domain.data_classes.params.TicketRestriction
import com.example.domain.enums.ui.TicketFieldsEnum
import com.example.domain.enums.TicketStatuses
import com.example.home.ui.common.impl.chip_rows.EquipmentChipRow
import com.example.home.ui.common.impl.chip_rows.ExecutorsChipRow
import com.example.home.ui.common.impl.chip_rows.FacilitiesChipRow
import com.example.home.ui.common.impl.chip_rows.QualityControllersChipRow
import com.example.home.ui.common.impl.chip_rows.TransportChipRow
import com.example.home.ui.common.impl.clickable_texts.KindClickableText
import com.example.home.ui.common.impl.clickable_texts.PriorityClickableText
import com.example.home.ui.common.impl.clickable_texts.ServiceClickableText
import com.example.home.ui.common.impl.date_pickers.ClosingDatePicker
import com.example.home.ui.common.impl.date_pickers.PlaneDatePicker
import com.example.home.ui.common.impl.drop_down_menu.StatusDropDownMenu
import com.example.home.ui.common.impl.text_fields.AssessedValueTextField
import com.example.home.ui.common.impl.texts.AuthorText
import com.example.home.ui.common.impl.texts.CreationDateText
import com.example.home.ui.common.impl.text_fields.CompletedWorkTextField
import com.example.home.ui.common.impl.text_fields.DescriptionTextField
import com.example.home.ui.common.impl.text_fields.ImprovementCommentTextField
import com.example.home.ui.common.impl.text_fields.NameTextField
import com.example.home.ui.common.impl.text_fields.ReasonForCancellationTextField
import com.example.home.ui.common.impl.text_fields.ReasonForRejectionTextField
import com.example.home.ui.common.impl.text_fields.ReasonForSuspensionTextField
import com.example.home.ui.common.impl.texts.DispatcherText
import com.example.home.ui.common.impl.texts.ExecutorsNominatorText
import com.example.home.ui.common.impl.texts.FieldText
import com.example.home.ui.common.impl.texts.QualityControllerNominatorText

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
            TicketFieldsEnum.STATUS -> StatusDropDownMenu(ticketRestriction.value.availableStatuses, params, updateRestrictions, ticketRestriction.value, selectedTicketStatus).Content()
            TicketFieldsEnum.AUTHOR -> AuthorText(params, ticket).Content()
            TicketFieldsEnum.FIELD -> FieldText(params, ticket).Content()
            TicketFieldsEnum.DISPATCHER -> DispatcherText(params, ticket).Content()
            TicketFieldsEnum.EXECUTORS_NOMINATOR -> ExecutorsNominatorText(params, ticket).Content()
            TicketFieldsEnum.QUALITY_CONTROLLERS_NOMINATOR -> QualityControllerNominatorText(params, ticket).Content()
            TicketFieldsEnum.CREATION_DATE -> CreationDateText(params, ticket).Content()
            TicketFieldsEnum.TICKET_NAME -> NameTextField(params, ticket).Content()
            TicketFieldsEnum.DESCRIPTION_OF_WORK -> DescriptionTextField(params, ticket).Content()
            TicketFieldsEnum.KIND -> KindClickableText(params, ticket).Content()
            TicketFieldsEnum.SERVICE -> ServiceClickableText(params, ticket).Content()
            TicketFieldsEnum.FACILITIES -> FacilitiesChipRow(ticketData?.facilities, params, ticket).Content()
            // TicketFieldsEnum.EQUIPMENT -> EquipmentChipRow(params, ticket).Content() TODO("Изменять в зависимости от выбранного объекта")
            TicketFieldsEnum.TRANSPORT -> TransportChipRow(ticketData?.transport, params, ticket).Content()
            TicketFieldsEnum.PRIORITY -> PriorityClickableText(params, ticket).Content()
            TicketFieldsEnum.ASSESSED_VALUE -> AssessedValueTextField(params, ticket).Content()
            TicketFieldsEnum.ASSESSED_VALUE_DESCRIPTION -> AssessedValueTextField(params, ticket).Content()
            TicketFieldsEnum.REASON_FOR_CANCELLATION -> ReasonForCancellationTextField(params, ticket).Content()
            TicketFieldsEnum.REASON_FOR_REJECTION -> ReasonForRejectionTextField(params, ticket).Content()
            TicketFieldsEnum.EXECUTORS -> ExecutorsChipRow(ticketData?.users, params, ticket).Content()
            TicketFieldsEnum.PLANE_DATE -> PlaneDatePicker(params, ticket).Content()
            TicketFieldsEnum.REASON_FOR_SUSPENSION -> ReasonForSuspensionTextField(params, ticket).Content()
            TicketFieldsEnum.COMPLETED_WORK -> CompletedWorkTextField(params, ticket).Content()
            TicketFieldsEnum.QUALITY_CONTROLLERS -> QualityControllersChipRow(ticketData?.users, params, ticket).Content()
            TicketFieldsEnum.IMPROVEMENT_COMMENT -> ImprovementCommentTextField(params, ticket).Content()
            TicketFieldsEnum.CLOSING_DATE -> ClosingDatePicker(params, ticket).Content()
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