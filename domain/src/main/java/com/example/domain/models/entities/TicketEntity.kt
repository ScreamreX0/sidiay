package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketEntity constructor (
    val id: Long? = 0,
    val facilities: List<FacilityEntity>?,
    val kindEntity: KindEntity?,
    val author: UserEntity?,
    val executor: UserEntity?,
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