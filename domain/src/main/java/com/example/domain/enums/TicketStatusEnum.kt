package com.example.domain.enums

enum class TicketStatusEnum(
    val id: Int,
    val title: String
) {
    NotFormed(id = 0, title = "Не сформирована"),
    New(id = 1, title = "Новая"),
    Accepted(id = 2, title = "Принята"),
    Denied(id = 3, title = "Отказано"),
    Paused(id = 4, title = "Приостановлена"),
    Done(id = 5, title = "Выполнена"),
    Closed(id = 6, title = "Закрыта"),
    ForRevision(id = 7, title = "На доработку");
}