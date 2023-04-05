package com.example.domain.data_classes.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KindEntity constructor (
    var id: Long,
    var name: String? = null
) : Parcelable