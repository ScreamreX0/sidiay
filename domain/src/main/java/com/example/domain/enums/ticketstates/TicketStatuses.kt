package com.example.domain.enums.ticketstates

enum class TicketStatuses(private val elementName: String) : ITicketStates {
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