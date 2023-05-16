package com.example.domain.enums

enum class KindsEnum(val value: Int, val label: String) {
    PLANE(1, "Плановый"),
    CURRENT(2, "Текущий"),
    CAPITAL(3, "Капитальный"),
    TO(4, "ТО"),
    PPR(5, "ППР"),
    FLOODED(6, "Пробы обводнены"),
    SLING_WORK(7, "Стропальные работы");

    companion object {
        fun getByValue(value: Int?) = KindsEnum.values().find { it.value == value }
    }
}