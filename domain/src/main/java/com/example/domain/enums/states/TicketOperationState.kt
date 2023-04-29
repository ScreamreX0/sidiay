package com.example.domain.enums.states

enum class TicketOperationState(val message: String?) {
    WAITING(null),
    IN_PROCESS(null),
    DONE("Заявка успешно сохранена"),
    OPERATION_ERROR("Ошибка сохранения"),
    CONNECTION_ERROR("Ошибка подключения")
}