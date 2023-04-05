package com.example.domain.data_classes.params

import com.example.domain.data_classes.entities.*

data class TicketData(
    val users: List<UserEntity>? = null,
    val equipment: List<EquipmentEntity>? = null,
    val facilities: List<FacilityEntity>? = null,
    val kinds: List<KindEntity>? = null,
    val priorities: List<PriorityEntity>? = null,
    val services: List<ServiceEntity>? = null,
    val transport: List<TransportEntity>? = null,
)
