package com.example.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat

@Parcelize
data class Application constructor (
    val id: Int,
    val service: String = "",
    val executor: Employee?,
    val type: String = "",
    val priority: String = "",
    val status: String = "",
    val plannedDate: SimpleDateFormat?,
    val expirationDate: SimpleDateFormat?,
    val description: String = "",
    val completedWorks: String = "",
    val author: Employee?,
    val creationDate: SimpleDateFormat?,
    val objects: List<Object>?
) : Parcelable