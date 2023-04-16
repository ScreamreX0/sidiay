package com.example.domain.enums

enum class TicketStatuses(
    val value: Int,
    val title: String,
) {
    NOT_FORMED(1, "Не сформирована"),
    NEW(2, "Новая"),
    ACCEPTED(3, "Принята"),
    DENIED(4, "Отклонена"),
    SUSPENDED(5, "Приостановлена"),
    COMPLETED(6, "Завершена"),
    CLOSED(7, "Закрыта"),
    FOR_REVISION(8, "На рассмотрение");
}