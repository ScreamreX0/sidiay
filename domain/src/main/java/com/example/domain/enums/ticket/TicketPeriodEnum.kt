package com.example.domain.enums.ticket

enum class TicketPeriodEnum(private val elementName: String, val periodInDays: Int) : ITicketEnum {
    Day("За день", 1),
    ThreeDays("За 3 дня", 3),
    Week("За неделю", 7),
    Month("За месяц", 28),
    AllTime("За все время", 0);

    override fun getName() = elementName
}