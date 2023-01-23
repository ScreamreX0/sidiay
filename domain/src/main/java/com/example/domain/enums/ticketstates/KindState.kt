package com.example.domain.enums.ticketstates

enum class KindState(private val elementName: String) : ITicketStates {
    Plane("Плановые"),
    Current("Текущий"),
    Capital("Капитальный"),
    TO("ТО"),
    PPR("ППР"),
    Watered("Пробы обводнен"),
    SlingingWork("Стропальные работы");

    override fun getName() = elementName
}