package com.example.domain.enums

enum class PrioritiesEnum(val value: Int, val label: String) {
    VERY_LOW(1, "Очень низкий"),
    LOW(2, "Низкий"),
    MEDIUM(3, "Средний"),
    HIGH(4, "Высокий"),
    URGENT(5, "Срочный");

    companion object {
        fun getByValue(priority: Int?) = PrioritiesEnum.values().find { it.value == priority }
    }
}
