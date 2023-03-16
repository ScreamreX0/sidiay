package com.example.domain.data_classes.entities

import android.os.Parcelable
import com.example.domain.enums.states.DraftState
import kotlinx.parcelize.Parcelize

@Parcelize
data class DraftEntity constructor (
    var id: Long? = 1,
    var name: String? = null,
    var facilities: List<FacilityEntity>? = null,
    var service: String? = null,
    var kind: KindEntity? = null,
    var executor: UserEntity? = null,
    var brigade: List<UserEntity>? = null,
    var priority: Int? = null,
    var plane_date: String? = null,
    var expiration_date: String? = null,
    var description: String? = null,
    var draftStatus: DraftState = DraftState.NEW
) : Parcelable