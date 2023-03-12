package com.example.domain.data_classes.params

import android.os.Parcelable
import com.example.domain.data_classes.entities.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthParams(
    val user: UserEntity? = null,
    val url: ConnectionParams? = null,  // url for network requests
    val darkMode: Boolean = false,  // false - light mode; true - dark mode
    val onlineMode: Boolean = true,  // false - offline mode; true - online mode
) : Parcelable