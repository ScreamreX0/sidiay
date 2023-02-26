package com.example.domain.enums.ticket

enum class TicketPeriodEnum(
    val periodInDays: Int,
    private val elementName: String
) : ITicketEnum {
    Day(elementName = "За день", periodInDays = 1),
    ThreeDays(elementName = "За 3 дня", periodInDays =  3),
    Week(elementName = "За неделю",  periodInDays = 7),
    Month(elementName = "За месяц",  periodInDays = 28),
    AllTime(elementName = "За все время", periodInDays =  0);

    override fun getName() = elementName
}