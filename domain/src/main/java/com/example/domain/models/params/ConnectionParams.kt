package com.example.domain.models.params

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ConnectionParams(val name: String, val url: String) : Parcelable