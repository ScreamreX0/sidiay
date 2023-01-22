package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticket constructor (
    val id: Long? = 0,
    val facilities: List<Facility>?,
    val kind: Kind?,
    val author: User?,
    val executor: User?,
    val priority: Int? = 1,
    val plane_date: String? = "",
    val expiration_date: String? = "",
    val creation_date: String? = "",
    val completed_work: String? = "",
    val description: String? = "",
    val name: String? = "",
    val status: String? = "",
    val service: String? = ""
) : Parcelable