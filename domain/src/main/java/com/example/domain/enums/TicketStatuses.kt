package com.example.domain.enums

enum class TicketStatuses(
    val value: Int
) {
    NOT_FORMED(1),
    NEW(2),
    ACCEPTED(3),
    DENIED(4),
    SUSPENDED(5),
    COMPLETED(6),
    CLOSED(7),
    FOR_REVISION(8);
}