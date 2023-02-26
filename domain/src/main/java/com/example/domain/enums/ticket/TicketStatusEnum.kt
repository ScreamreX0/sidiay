package com.example.domain.enums.ticket

enum class TicketStatusEnum(
    val id: Int,
    private val elementName: String
) : ITicketEnum {
    NotFormed(elementName = "Не сформирована", id = 1),
    New(elementName = "Новая", id = 2),
    Accepted(elementName = "Принята", id = 3),
    Denied(elementName = "Отказано", id = 4),
    Paused(elementName = "Приостановлена", id = 5),
    Done(elementName = "Выполнена", id = 6),
    Closed(elementName = "Закрыта", id = 7),
    ForRevision(elementName = "На доработку", id = 8);

    override fun getName() = elementName
}