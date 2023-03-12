package com.example.domain.data_classes.params

import com.example.domain.data_classes.entities.FacilityEntity
import com.example.domain.data_classes.entities.UserEntity
import com.example.domain.enums.*

data class TicketCreateParams(
    val kinds: List<TicketKindEnum> = listOf(),
    val facilities: List<FacilityEntity> = listOf(),
    val periods: List<TicketPeriodEnum> = listOf(),
    val priorities: List<TicketPriorityEnum> = listOf(),
    val services: List<TicketServiceEnum> = listOf(),
    val statuses: List<TicketStatusEnum> = listOf(),
    val users: List<UserEntity> = listOf(),
)