package com.example.domain.data_classes.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity constructor(
    var id: Long,
    var employee: EmployeeEntity? = null,
    var password: String? = null,
    var phone: String? = null,
    var photo: String? = null,
) : Parcelable {
    fun getFullName(): String? {
        return employee?.let {
            if (it.name.isNullOrBlank() || it.firstname.isNullOrBlank()) {
                null
            } else if (it.lastname.isNullOrBlank()) {
                "$it.firstname ${it.name!!.first()}."
            } else {
                "$it.firstname ${it.name!!.first()}. ${it.lastname!!.first()}."
            }
        }
    }
}
