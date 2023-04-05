package com.example.domain.data_classes.entities

import android.os.Parcelable
import com.example.domain.enums.TicketStatuses
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class TicketEntity constructor (
    var id: Long,
    var priority: PriorityEntity? = null,
    var service: ServiceEntity? = null,
    var kind: KindEntity? = null,
    var author: UserEntity? = null,
    var executor: UserEntity? = null,
    var brigade: List<UserEntity>? = null,
    var transport: List<TransportEntity>? = null,
    var facilities: List<FacilityEntity>? = null,
    var equipment: List<EquipmentEntity>? = null,
    var plane_date: Date? = null,
    var expiration_date: Date? = null,
    var creation_date: Date? = null,
    var completed_work: String? = null,
    var description: String? = null,
    var name: String? = null,
    var status: TicketStatuses? = null,
) : Parcelable