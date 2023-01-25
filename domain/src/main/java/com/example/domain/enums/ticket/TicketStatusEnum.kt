package com.example.domain.enums.ticket

enum class TicketStatusEnum(private val elementName: String, val id: Int) : ITicketEnum {
    NotFormed("Не сформирована", 1),
    New("Новая", 2),
    Accepted("Принята", 3),
    Denied("Отказано", 4),
    Paused("Приостановлена", 5),
    Done("Выполнена", 6),
    Closed("Закрыта", 7),
    ForRevision("На доработку", 8);

    override fun getName() = elementName
}