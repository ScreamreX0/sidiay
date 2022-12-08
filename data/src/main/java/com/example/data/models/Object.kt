package com.example.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Object constructor (
    val id: Int,
    val name: String = ""
) : Parcelable
