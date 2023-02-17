package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketEntity constructor (
    var id: Long? = 1,
    var facilities: List<FacilityEntity>? = listOf(),
    var kind: KindEntity? = KindEntity(),
    var author: UserEntity? = UserEntity(),
    var executor: UserEntity? = UserEntity(),
    var priority: Long? = 1,
    var plane_date: String? = "",
    var expiration_date: String? = "",
    var creation_date: String? = "",
    var completed_work: String? = "",
    var description: String? = "",
    var name: String? = "",
    var status: String? = "",
    var service: String? = ""
) : Parcelable