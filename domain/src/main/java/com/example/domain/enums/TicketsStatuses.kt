package com.example.domain.enums

enum class TicketsStatuses(name: String) {
    NotFormed("Не сформирована"),
    New("Новая"),
    Accepted("Принята"),
    Denied("Отказано"),
    Paused("Приостановлена"),
    Done("Выполнена"),
    Closed("Закрыта"),
    ForRevision("На доработку")
}