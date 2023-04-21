package com.example.domain.types

import android.os.Bundle
import androidx.navigation.NavType
import com.example.domain.data_classes.entities.TicketEntity
import com.google.gson.Gson

class TicketEntityType : NavType<TicketEntity>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): TicketEntity? = bundle.getParcelable(key)
    override fun parseValue(value: String): TicketEntity = Gson().fromJson(value, TicketEntity::class.java)
    override fun put(bundle: Bundle, key: String, value: TicketEntity) = bundle.putParcelable(key, value)
}