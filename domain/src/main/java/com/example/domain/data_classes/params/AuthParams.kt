package com.example.domain.data_classes.params

import android.os.Parcelable
import com.example.domain.data_classes.entities.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthParams(
    val user: UserEntity? = null,
    val connectionParams: ConnectionParams? = null,
    val darkMode: Boolean? = false,
) : Parcelable