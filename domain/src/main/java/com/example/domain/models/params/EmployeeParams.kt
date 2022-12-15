package com.example.domain.models.params

data class EmployeeParams(
    val id: Int,
    val firstName: String,
    val name: String,
    val lastName: String,
    var isSelected: Boolean
)
