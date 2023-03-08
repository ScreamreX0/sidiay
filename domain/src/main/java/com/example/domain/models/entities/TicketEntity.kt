package com.example.domain.models.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketEntity constructor (
    var id: Long? = 1,
    var facilities: List<FacilityEntity>? = null,
    var kind: KindEntity? = null,
    var author: UserEntity? = null,
    var executor: UserEntity? = null,
    var priority: Long? = 1,  // 1-5 (1 - very low)
    var plane_date: String? = null,
    var expiration_date: String? = null,
    var creation_date: String? = null,
    var completed_work: String? = null,
    var description: String? = null,
    var name: String? = null,
    var status: String? = null,
    var service: String? = null,
) : Parcelable