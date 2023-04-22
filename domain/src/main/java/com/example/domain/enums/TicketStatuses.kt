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

    ACCEPTED(
        value = 3,
        title = "Принята",
    ),

    DENIED(
        value = 4,
        title = "Отклонена"
    ),

    SUSPENDED(
        value = 5,
        title = "Приостановлена"
    ),

    COMPLETED(
        value = 6,
        title = "Завершена"
    ),

    CLOSED(
        value = 7,
        title = "Закрыта"
    ),

    FOR_REVISION(
        value = 8,
        title = "На доработку"
    );
}