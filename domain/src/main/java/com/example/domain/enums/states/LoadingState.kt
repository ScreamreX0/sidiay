package com.example.domain.enums.states

enum class LoadingState(val message: String?) {
    WAIT_FOR_INIT(null),
    LOADING("Загрузка"),
    DONE("Готово"),
    ERROR("Ошибка"),
    CONNECTION_ERROR("Ошибка подключения")
}