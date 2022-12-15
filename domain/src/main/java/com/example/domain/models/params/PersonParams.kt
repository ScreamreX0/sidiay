package com.example.domain.models.params

data class PersonParams(
    val id: Int,
    val firstName: String,
    val name: String,
    val lastName: String,
    var isSelected: Boolean
)
