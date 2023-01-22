package com.example.domain.enums

enum class Priorities(val title: String, priority: Int) {
    VeryLow("Очень низкий", 1),
    Low("Низкий", 2),
    Medium("Средний", 3),
    High("Высокий", 4),
    Urgent("Срочный", 5);

    companion object {
        fun valueOf(priority: Int): Priorities {
            return when (priority) {
                2 -> Low
                3 -> Medium
                4 -> High
                5 -> Urgent
                else -> VeryLow
            }
        }
    }
}