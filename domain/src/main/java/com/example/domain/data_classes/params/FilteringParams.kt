package com.example.domain.data_classes.params

import com.example.domain.data_classes.entities.EquipmentEntity
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.TransportEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.enums.KindsEnum
import com.example.domain.enums.PrioritiesEnum
import com.example.domain.enums.ServicesEnum
import com.example.domain.enums.TicketStatuses

data class FilteringParams(
    val priority: List<PrioritiesEnum> = emptyList(),
    val services: List<ServicesEnum> = emptyList(),
    val kinds: List<KindsEnum> = emptyList(),
    val authors: List<UserEntity> = emptyList(),
    val executors: List<UserEntity> = emptyList(),
    val brigade: List<UserEntity> = emptyList(),
    val transport: List<TransportEntity> = emptyList(),
    val facilities: List<FacilityEntity> = emptyList(),
    val equipment: List<EquipmentEntity> = emptyList(),
    val status: List<TicketStatuses> = emptyList(),
    val planeDate: String? = null,
    val closingDate: String? = null,
    val creationDate: String? = null,
)
