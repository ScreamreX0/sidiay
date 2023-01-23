package com.example.domain.enums.ticketstates

enum class PriorityState(private val elementName: String, priority: Int) : ITicketStates {
    VeryLow("Очень низкий", 1),
    Low("Низкий", 2),
    Medium("Средний", 3),
    High("Высокий", 4),
    Urgent("Срочный", 5);

    companion object {
        fun valueOf(priority: Int): PriorityState {
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