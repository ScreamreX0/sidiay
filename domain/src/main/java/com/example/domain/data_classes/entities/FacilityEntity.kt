package com.example.domain.data_classes.entities

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class FacilityEntity constructor(
    var id: Long,
    var name: String? = null,
    var field: FieldsEntity? = null,
    var equipment: List<EquipmentEntity>? = null
) : Parcelable
