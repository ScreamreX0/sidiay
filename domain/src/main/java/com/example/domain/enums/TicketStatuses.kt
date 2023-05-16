package com.example.domain.enums

enum class TicketStatuses(
    val value: Int,
    val title: String
) {
    NOT_FORMED(
        value = 1,
        title = "Не сформирована",
    ),

    NEW(
        value = 2,
        title = "Новая",
    ),

    EVALUATED(
        value = 3,
        title = "Оценена",
    ),

    REJECTED(
        value = 4,
        title = "Отклонена",
    ),

    ACCEPTED(
        value = 5,
        title = "Принята",
    ),

    SUSPENDED(
        value = 6,
        title = "Приостановлена"
    ),

    COMPLETED(
        value = 7,
        title = "Завершена"
    ),

    QUALITY_CHECKING(
        value = 8,
        title = "Контроль качества"
    ),

    CLOSED(
        value = 9,
        title = "Закрыта"
    ),

    FOR_REVISION(
        value = 10,
        title = "На доработку"
    ),

    CANCELED(
        value = 11,
        title = "Отменена"
    );

    companion object {
        fun get(status: Int?) = TicketStatuses.values().find { it.value == status }
    }
}