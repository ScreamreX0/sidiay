package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KindEntity constructor (
    val id: Long = 1,
    val name: String = ""
) : Parcelable