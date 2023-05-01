package com.example.domain.enums.states

enum class NetworkState(val title: String?) : INetworkState {
    WAIT_FOR_INIT(null),
    LOADING(null),
    DONE(null),
    ERROR("Ошибка"),
    NO_SERVER_CONNECTION("Нет соединения с сервером"),
    CONNECTION_ESTABLISHED("Соединение установлено");

    override fun toString() = title ?: ""
}