package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User constructor (
    val id: Int = 0,
    val password: String = "default password",
    val username: String = "default username",
    val email: String = "default email",
) : Parcelable