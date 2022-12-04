package com.example.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User constructor (
    val id: Int = 0,
    val password: String = "",
    val username: String = "",
    val confirmed: Boolean = false,
    val email: String = "",
    val token: String = "",
    val code: String = "",
) : Parcelable