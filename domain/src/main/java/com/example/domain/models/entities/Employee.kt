package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Employee constructor(
    val id: Int,
    val firstName: String = "Ikhsanov",
    val name: String = "Ruslan",
    val lastName: String = "Lenarovich"
) : Parcelable
