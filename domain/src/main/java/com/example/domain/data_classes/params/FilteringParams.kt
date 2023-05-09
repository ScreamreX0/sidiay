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
    val priority: List<PriorityEntity>,
    val service: List<ServiceEntity>,
    val kind: List<KindEntity>,
    val author: List<UserEntity>,
    val executor: List<UserEntity>,
    val brigade: List<UserEntity>,
    val transport: List<TransportEntity>,
    val facilities: List<FacilityEntity>,
    val equipment: List<EquipmentEntity>,
    val status: List<TicketStatuses>,
    val planeDate: String,
    val closingDate: String,
    val creationDate: String,
)
