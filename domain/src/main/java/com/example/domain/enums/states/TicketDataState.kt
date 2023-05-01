package com.example.domain.enums.states

enum class TicketDataState(val title: String) : INetworkState {
    ERROR("Ошибка при получении данных");

    override fun toString() = title
}