package com.example.core.utils

import android.content.Context
import android.net.Uri
import android.os.Parcelable
import android.widget.Toast
import com.google.gson.Gson

class Helper {
    companion object {
        fun showShortToast(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

        fun showLongToast(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }

        fun <T: Parcelable> parcelableToString(obj: T): String = Uri.encode(Gson().toJson(obj))

        fun <T> objToJson(obj: T?): String = Gson().toJson(obj)

        inline fun <reified T> objFromJson(string: String?): T? = string?.let { Gson().fromJson(string, T::class.java) }

        fun <T> addToList(list: List<T>?, addingItem: T): List<T> {
            val newList = list?.toMutableList() ?: mutableListOf()
            newList.add(addingItem)
            return newList.toList()
        }

        fun <T> removeFromList(list: List<T>?, removingItem: T): List<T> {
            val newList = list?.toMutableList() ?: mutableListOf()
            newList.remove(removingItem)
            return newList.toList()
        }
    }
}