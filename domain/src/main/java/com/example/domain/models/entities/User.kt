package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User constructor (
    val id: Long = 0,
    val firstname: String = "",
    val name: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String = "",
    val photo: String? = "",
) : Parcelable {
    fun getEmptyUser(): User {
        return User(
            id = 0,
            firstname = "Ikhsanov",
            name = "Ruslan",
            lastname = "Lenarovich",
            email = "default email",
            password = "default password",
            phone = "default phone",
            photo = "default photo",
        )
    }
}