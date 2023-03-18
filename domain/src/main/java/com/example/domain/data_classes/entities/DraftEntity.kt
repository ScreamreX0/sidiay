package com.example.domain.data_classes.entities

import android.os.Parcelable
import com.example.domain.enums.TicketKindEnum
import com.example.domain.enums.TicketPriorityEnum
import com.example.domain.enums.TicketServiceEnum
import com.example.domain.enums.states.DraftState
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class DraftEntity constructor (
    var name: String? = null,
    var facilities: List<FacilityEntity>? = null,
    var service: TicketServiceEnum? = null,
    var kind: TicketKindEnum? = null,
    var executor: UserEntity? = null,
    var brigade: List<UserEntity>? = null,
    var priority: TicketPriorityEnum? = null,
    var plane_date: LocalDate? = null,
    var expiration_date: LocalDate? = null,
    var description: String? = null,
    var draftStatus: DraftState = DraftState.NEW
) : Parcelable