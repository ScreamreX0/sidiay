package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User constructor (
    val id: Int = 0,
    val firstName: String = "Ikhsanov",
    val name: String = "Ruslan",
    val lastName: String = "Lenarovich",
    val email: String = "default email",
    val password: String = "default password",
    val phone: String = "default phone",
    val photo: String = "default photo",
) : Parcelable