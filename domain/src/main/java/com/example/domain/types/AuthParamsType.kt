package com.example.domain.types

import android.os.Bundle
import androidx.navigation.NavType
import com.example.domain.models.entities.UserEntity
import com.example.domain.models.params.AuthParams
import com.google.gson.Gson


class AuthParamsType : NavType<AuthParams>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): AuthParams? = bundle.getParcelable(key)
    override fun parseValue(value: String): AuthParams = Gson().fromJson(value, AuthParams::class.java)
    override fun put(bundle: Bundle, key: String, value: AuthParams) = bundle.putParcelable(key, value)
}