package com.example.domain.enums

enum class TicketPeriodEnum(
    val id: Int,
    val periodInDays: Int,
    val title: String
) {
    Day(id = 0, title = "За день", periodInDays = 1),
    ThreeDays(id = 1, title = "За 3 дня", periodInDays = 3),
    Week(id = 2, title = "За неделю", periodInDays = 7),
    Month(id = 3, title = "За месяц", periodInDays = 28),
    AllTime(id = 4, title = "За все время", periodInDays = 0);
}