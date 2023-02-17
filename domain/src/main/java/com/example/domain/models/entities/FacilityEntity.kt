package com.example.domain.models.entities

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class FacilityEntity constructor(
    val id: Long,
    val name: String = ""
) : Parcelable
