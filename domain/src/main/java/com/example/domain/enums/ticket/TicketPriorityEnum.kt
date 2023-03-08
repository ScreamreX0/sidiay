package com.example.domain.enums.ticket

enum class TicketPriorityEnum(
    val priority: Long,
    private val elementName: String
) : ITicketEnum {
    VeryLow(elementName = "Очень низкий", priority = 1),
    Low(elementName = "Низкий", priority = 2),
    Medium(elementName = "Средний", priority = 3),
    High(elementName = "Высокий", priority = 4),
    Urgent(elementName = "Срочный", priority = 5),
    Unknown(elementName = "Неизвестный", priority = 0);

    companion object {
        fun valueOf(priority: Long?): TicketPriorityEnum {
            return when (priority) {
                1L -> VeryLow
                2L -> Low
                3L -> Medium
                4L -> High
                5L -> Urgent
                else -> Unknown
            }
        }
    }

    override fun getName() = elementName
}