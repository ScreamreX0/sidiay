package com.example.domain.enums

enum class Period(name: String, periodInDays: Int) {
    Day("За день", 1),
    ThreeDays("За 3 дня", 3),
    Week("За неделю", 7),
    Month("За месяц", 28),
    AllTime("За все время", 0)
}