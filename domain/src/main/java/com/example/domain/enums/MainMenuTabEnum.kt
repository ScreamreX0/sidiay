package com.example.domain.enums

enum class MainMenuTabEnum(
    val id: Int,
    val title: String,
) {
    TICKETS(0, "Заявки"),
    DRAFTS(1, "Черновики"),
}