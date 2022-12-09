package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Object constructor (
    val id: Int,
    val name: String = ""
) : Parcelable
