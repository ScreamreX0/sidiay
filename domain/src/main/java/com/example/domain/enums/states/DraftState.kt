package com.example.domain.enums.states

enum class DraftState(
    val id: Int,
    val title: String
) {
    NEW(0, "Новый"),
    SENT(1, "Отправлен"),
}