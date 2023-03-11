package com.example.domain.data_classes.entities

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class FacilityEntity constructor(
    val id: Long,
    val name: String = ""
) : Parcelable
