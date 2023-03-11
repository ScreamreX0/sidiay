package com.example.domain.enums

enum class TicketKindEnum(
    val id: Long,
    val title: String
) {
    Plane(id = 0, title = "Плановые"),
    Current(id = 1, title = "Текущий"),
    Capital(id = 2, title = "Капитальный"),
    TO(id = 3, title = "ТО"),
    PPR(id = 4, title = "ППР"),
    Watered(id = 5, title = "Пробы обводнен"),
    SlingingWork(id = 6, title = "Стропальные работы");
}