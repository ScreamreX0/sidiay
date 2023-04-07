package com.example.domain.data_classes.params

import android.os.Parcelable
import com.example.domain.data_classes.entities.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthParams(
    val user: UserEntity? = null,
    val connectionParams: ConnectionParams? = null,  // url for network requests
    val darkMode: Boolean? = false,  // false - light mode; true - dark mode
) : Parcelable