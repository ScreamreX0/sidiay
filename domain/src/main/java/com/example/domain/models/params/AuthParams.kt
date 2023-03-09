package com.example.domain.models.params

import android.os.Parcelable
import com.example.domain.models.entities.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthParams(
    val user: UserEntity? = null,
    val url: ConnectionParams? = null,  // url for network requests
    val darkMode: Boolean = false,  // false - light mode; true - dark mode
    val networkMode: Boolean = false,  // false - offline mode; true - online mode
) : Parcelable