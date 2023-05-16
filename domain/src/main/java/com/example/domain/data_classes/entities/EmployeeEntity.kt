package com.example.domain.data_classes.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeEntity(
    var id: Long,
    var firstname: String? = null,
    var name: String? = null,
    var lastname: String? = null,
    var email: String? = null,
    var jobTitle: Int? = null,
    var subdivision: SubdivisionEntity? = null,
    var facility: FacilityEntity? = null
) : Parcelable
