package com.example.domain.enums.ticket

enum class TicketKindEnum(
    val id: Long,
    private val elementName: String
) : ITicketEnum {
    Plane(elementName = "Плановые", id = 1),
    Current(elementName = "Текущий", id = 2),
    Capital(elementName = "Капитальный", id = 3),
    TO(elementName = "ТО", id = 4),
    PPR(elementName = "ППР", id = 5),
    Watered(elementName = "Пробы обводнен", id = 6),
    SlingingWork(elementName = "Стропальные работы", id = 7);

    override fun getName() = elementName
}