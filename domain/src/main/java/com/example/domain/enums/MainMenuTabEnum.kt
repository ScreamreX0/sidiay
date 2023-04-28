package com.example.domain.enums

enum class MainMenuTabEnum(
    val id: Int,
    val title: String,
) {
    USER_AUTHOR_TICKETS(0, "Ваши заявки"),
    USER_EXECUTOR_TICKETS(1, "Заявки на исполнение"),
    DRAFTS(2, "Черновики"),
}