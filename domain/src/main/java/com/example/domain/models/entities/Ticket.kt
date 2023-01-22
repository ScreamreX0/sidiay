package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Ticket constructor (
    val id: Int,
    val facilities: List<Facility>,
    val kind: Kind,
    val author: User?,
    val executor: User?,
    val priority: Int,
    val planeDate: java.sql.Date,
    val expirationDate: java.sql.Date,
    val creationDate: java.sql.Date,
    val completedWork: String,
    val description: String,
    val name: String,
    val status: String,
    val service: String
) : Parcelable