package com.example.domain.enums.states

enum class NetworkConnectionState(val title: String?) {
    NO_NETWORK_CONNECTION("Нет интернет соединения"),
    NO_SERVER_CONNECTION("Нет соединения с сервером"),
    CONNECTION_ESTABLISHED("Соединение установлено"),
    WAITING(null);
}