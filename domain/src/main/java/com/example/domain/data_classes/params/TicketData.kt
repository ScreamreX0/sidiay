package com.example.domain.data_classes.params

import com.example.domain.data_classes.entities.*

data class TicketData(
    val users: List<UserEntity>? = null,
    val facilities: List<FacilityEntity>? = null,
    val transport: List<TransportEntity>? = null,
)
