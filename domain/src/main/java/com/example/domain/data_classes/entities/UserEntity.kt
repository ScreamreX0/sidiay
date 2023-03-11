package com.example.domain.data_classes.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity constructor(
    val id: Long = -1,
    val firstname: String? = null,
    val name: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phone: String? = null,
    val photo: String? = null,
) : Parcelable {
    fun getFullName(): String? {
        return if (name.isNullOrBlank() || firstname.isNullOrBlank()) {
            null
        } else if (lastname.isNullOrBlank()) {
            "$firstname ${name.first()}."
        } else {
            "$firstname ${name.first()}. ${lastname.first()}."
        }
    }
}
