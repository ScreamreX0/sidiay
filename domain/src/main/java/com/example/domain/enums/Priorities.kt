package com.example.domain.enums

enum class Priorities(name: String, priority: Int) {
    VeryLow("Очень низкий", 1),
    Low("Низкий", 2),
    Medium("Средний", 3),
    High("Высокий", 4),
    Urgent("Срочный", 5)
}