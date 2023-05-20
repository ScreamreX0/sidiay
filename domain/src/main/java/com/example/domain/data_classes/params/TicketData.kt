package com.example.domain.data_classes.params

import com.example.domain.data_classes.entities.*

data class TicketData(
    var users: List<UserEntity>? = null,
    var facilities: List<FacilityEntity>? = null,
    var transport: List<TransportEntity>? = null,
)
