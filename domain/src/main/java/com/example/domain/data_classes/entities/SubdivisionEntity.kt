package com.example.domain.data_classes.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubdivisionEntity(
    var id: Long,
    var name: String? = null,
    var field: FieldsEntity? = null
) : Parcelable
