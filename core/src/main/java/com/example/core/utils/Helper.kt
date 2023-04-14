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
    }
}