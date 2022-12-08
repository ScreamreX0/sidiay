package com.example.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate

@Parcelize
data class Application constructor (
    val id: Int,
    val service: String = "",
    val executor: Employee?,
    val type: String = "",
    val priority: String = "",
    val status: String = "",
    val plannedDate: String = "",
    val expirationDate: String = "",
    val description: String = "",
    val completedWorks: String = "",
    val author: Employee?,
    val creationDate: String = "",
    val objects: List<Object>?
) : Parcelable