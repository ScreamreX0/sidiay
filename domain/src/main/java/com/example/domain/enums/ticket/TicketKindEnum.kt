package com.example.domain.enums.ticket

enum class TicketKindEnum(private val elementName: String, val id: Long) : ITicketEnum {
    Plane("Плановые", 1),
    Current("Текущий", 2),
    Capital("Капитальный", 3),
    TO("ТО", 4),
    PPR("ППР", 5),
    Watered("Пробы обводнен", 6),
    SlingingWork("Стропальные работы", 7);

    override fun getName() = elementName
}