package com.example.domain.models.params

import com.example.domain.enums.ticket.TicketStatusEnum
import com.example.domain.models.entities.FacilityEntity
import com.example.domain.models.entities.KindEntity
import com.example.domain.models.entities.UserEntity

data class AddTicketParams(
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
    var status: String? = TicketStatusEnum.New.getName(),
    var service: String? = null
)