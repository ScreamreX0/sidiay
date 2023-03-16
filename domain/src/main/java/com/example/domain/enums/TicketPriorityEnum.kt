package com.example.domain.enums

enum class TicketPriorityEnum(
    val priority: Int,
    val title: String
) {
    VeryLow(priority = 1, title = "Очень низкий"),
    Low(priority = 2, title = "Низкий"),
    Medium(priority = 3, title = "Средний"),
    High(priority = 4, title = "Высокий"),
    Urgent(priority = 5, title = "Срочный");

    companion object {
        fun get(priority: Int?): TicketPriorityEnum? {
            return priority?.let {
                TicketPriorityEnum.values().firstOrNull {
                    it.priority == priority
                }
            }
        }
    }
}