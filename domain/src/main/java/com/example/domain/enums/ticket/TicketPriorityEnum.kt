package com.example.domain.enums.ticket

enum class TicketPriorityEnum(
    val priority: Int,
    private val elementName: String
) : ITicketEnum {
    VeryLow(elementName = "Очень низкий", priority = 1),
    Low(elementName = "Низкий", priority = 2),
    Medium(elementName = "Средний", priority = 3),
    High(elementName = "Высокий", priority = 4),
    Urgent(elementName = "Срочный", priority = 5);

    companion object {
        fun valueOf(priority: Int): TicketPriorityEnum {
            return when (priority) {
                2 -> Low
                3 -> Medium
                4 -> High
                5 -> Urgent
                else -> VeryLow
            }
        }
    }

    override fun getName() = elementName
}