package com.example.domain.types

import android.os.Bundle
import androidx.navigation.NavType
import com.example.domain.models.entities.UserEntity
import com.google.gson.Gson


class UserType : NavType<UserEntity>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): UserEntity? = bundle.getParcelable(key)

    override fun parseValue(value: String): UserEntity =
        Gson().fromJson(value, UserEntity::class.java)

    override fun put(bundle: Bundle, key: String, value: UserEntity) =
        bundle.putParcelable(key, value)
}