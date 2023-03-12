package com.example.domain.data_classes.params

import com.example.domain.enums.TicketStatusEnum
import com.example.domain.data_classes.entities.KindEntity

data class CreateTicketParams(
    var id: Long? = 1,
//    var facilities: List<FacilityEntity>? = listOf(),
//    var author: UserEntity? = UserEntity(),
//    var executor: UserEntity? = UserEntity(),
    var priority: Long? = null,
    var kind: KindEntity? = null,
    var plane_date: String? = "",
    var expiration_date: String? = "",
    var creation_date: String? = "",
    var completed_work: String? = "",
    var description: String? = "",
    var name: String? = "",
    var status: String? = TicketStatusEnum.New.title,
    var service: String? = null
)