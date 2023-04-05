package com.example.domain.enums.states

enum class ConnectionState(val title: String?) {
    ESTABLISHED("Соединение установлено"),
    NOT_ESTABLISHED("Соединение не установлено"),
    WAITING(null);
}