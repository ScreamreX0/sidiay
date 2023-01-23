package com.example.domain.enums.ticketstates

enum class ServiceState(private val elementName: String) : ITicketStates{
    NPO("НПО"),
    Energo("Энерго"),
    KIP("КИП"),
    Welding("Сварочные работы"),
    PRS("ПРС"),
    Research("Исследование"),
    ConstructionWorks("Строительные работы");

    override fun getName() = elementName
}