package com.example.domain.data_classes.params

import com.example.domain.data_classes.entities.EquipmentEntity
import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.KindEntity
import com.example.domain.data_classes.entities.PriorityEntity
import com.example.domain.data_classes.entities.ServiceEntity
import com.example.domain.data_classes.entities.TransportEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.enums.TicketStatuses

data class FilteringParams(
    val priority: List<PriorityEntity> = emptyList(),
    val services: List<ServiceEntity> = emptyList(),
    val kinds: List<KindEntity> = emptyList(),
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
