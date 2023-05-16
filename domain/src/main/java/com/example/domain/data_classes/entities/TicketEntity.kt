package com.example.domain.data_classes.entities

import android.os.Parcelable
import com.example.domain.enums.KindsEnum
import com.example.domain.enums.ServicesEnum
import com.example.domain.enums.TicketStatuses
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketEntity constructor (
    var id: Long = -1,
    var status: Int? = null,
    var author: UserEntity? = null,
    var field: FieldsEntity? = null,
    var dispatcher: UserEntity? = null,
    var executors_nominator: UserEntity? = null,
    var quality_controllers_nominator: UserEntity? = null,
    var creation_date: String? = null,
    var ticket_name: String? = null,
    var description_of_work: String? = null,
    var kind: Int? = null,
    var service: Int? = null,
    var facilities: List<FacilityEntity>? = null,
    var equipment: List<EquipmentEntity>? = null,
    var transport: List<TransportEntity>? = null,
    var priority: Int? = null,
    var assessed_value: Float? = null,
    var assessed_value_description: String? = null,
    var reason_for_cancellation: String? = null,
    var reason_for_rejection: String? = null,
    var executors: List<UserEntity>? = null,
    var plane_date: String? = null,
    var reason_for_suspension: String? = null,
    var completed_work: String? = null,
    var quality_controllers: List<UserEntity>? = null,
    var improvement_comment: String? = null,
    var closing_date: String? = null
) : Parcelable