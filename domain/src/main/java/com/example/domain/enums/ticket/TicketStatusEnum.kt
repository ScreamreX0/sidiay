package com.example.domain.enums.ticket

enum class TicketStatusEnum(private val elementName: String) : ITicketEnum {
    NotFormed("Не сформирована"),
    New("Новая"),
    Accepted("Принята"),
    Denied("Отказано"),
    Paused("Приостановлена"),
    Done("Выполнена"),
    Closed("Закрыта"),
    ForRevision("На доработку");

    override fun getName() = elementName
}