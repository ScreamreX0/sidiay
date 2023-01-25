package com.example.domain.enums.ticket

enum class TicketServiceEnum(private val elementName: String) : ITicketEnum{
    NPO("НПО"),
    Energo("Энерго"),
    KIP("КИП"),
    Welding("Сварочные работы"),
    PRS("ПРС"),
    Research("Исследование"),
    ConstructionWorks("Строительные работы");

    override fun getName() = elementName
}