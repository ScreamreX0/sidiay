package com.example.domain.enums

enum class TicketStatuses(
    val value: Int,
    val title: String,
    val requiredFields: List<TicketFieldsEnum>? = null
) {
    NOT_FORMED(
        value = 1,
        title = "Не сформирована",
    ),

    NEW(
        value = 2,
        title = "Новая",
    ),

    ACCEPTED(
        value = 3,
        title = "Принята",
    ),

    DENIED(
        value = 4,
        title = "Отклонена",
        requiredFields = listOf(
            TicketFieldsEnum.COMPLETED_WORK,
            TicketFieldsEnum.CLOSING_DATE
        )
    ),

    SUSPENDED(
        value = 5,
        title = "Приостановлена"
    ),

    COMPLETED(
        value = 6,
        title = "Завершена",
        requiredFields = listOf(
            TicketFieldsEnum.COMPLETED_WORK,
            TicketFieldsEnum.CLOSING_DATE
        )
    ),

    CLOSED(
        value = 7,
        title = "Закрыта"
    ),

    FOR_REVISION(
        value = 8,
        title = "На доработку",
        requiredFields = listOf(TicketFieldsEnum.IMPROVEMENT_REASON)
    );
}