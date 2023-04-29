package com.example.domain.data_classes.params

import android.os.Parcelable
import com.example.core.utils.ConstAndVars
import kotlinx.parcelize.Parcelize


@Parcelize
data class ConnectionParams(
    val name: String = "Стандартное соединение",
    val url: String = ConstAndVars.URL
) : Parcelable