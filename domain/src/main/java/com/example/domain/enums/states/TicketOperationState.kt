package com.example.domain.enums.states

enum class TicketOperationState(val title: String): INetworkState {
    WAITING("Ожидание"),
    IN_PROCESS("В процессе"),
    DONE("Заявка успешно сохранена"),
    ERROR("Ошибка сохранения"),
    FILL_ALL_REQUIRED_FIELDS("Заполните все поля помеченные звездой");

    override fun toString() = title
}