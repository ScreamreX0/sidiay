package com.example.domain.enums

enum class TicketServiceEnum(
    val id: Int,
    val title: String
) {
    NPO(id = 0, title = "НПО"),
    Energo(id = 1, title = "Энерго"),
    KIP(id = 2, title = "КИП"),
    Welding(id = 3, title = "Сварочные работы"),
    PRS(id = 4, title = "ПРС"),
    Research(id = 5, title = "Исследование"),
    ConstructionWorks(id = 6, title = "Строительные работы");
}