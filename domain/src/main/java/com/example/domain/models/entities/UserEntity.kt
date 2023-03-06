package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity constructor (
    val id: Long = 0,
    val firstname: String = "",
    val name: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String = "",
    val photo: String = "",
) : Parcelable
