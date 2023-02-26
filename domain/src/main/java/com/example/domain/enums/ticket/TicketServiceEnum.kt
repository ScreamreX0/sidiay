package com.example.domain.enums.ticket

enum class TicketServiceEnum(
    private val elementName: String
) : ITicketEnum {
    NPO(elementName = "НПО"),
    Energo(elementName = "Энерго"),
    KIP(elementName = "КИП"),
    Welding(elementName = "Сварочные работы"),
    PRS(elementName = "ПРС"),
    Research(elementName = "Исследование"),
    ConstructionWorks(elementName = "Строительные работы");

    override fun getName() = elementName
}