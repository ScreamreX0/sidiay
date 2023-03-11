package com.example.domain.enums.states

enum class DraftState(
    val id: Int,
    val title: String
) {
    CREATED(0, "Создан"),
    SENT(1, "Отправлен"),
}