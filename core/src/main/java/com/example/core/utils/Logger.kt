package com.example.core.utils

import android.util.Log

class Logger {
    companion object {
        private const val defaultTag = "DEBUG"
        fun m(vararg text: String?) {
            text.forEach {
                it?.let {
                    Log.println(Log.INFO, defaultTag, it)
                } ?: Log.println(Log.INFO, defaultTag, "null")
            }
        }
        fun e(text: String, tag: String) { Log.println(Log.ERROR, tag, text) }
        fun e(text: String) { Log.println(Log.ERROR, defaultTag, text) }
        fun w(text: String, tag: String) { Log.println(Log.WARN, tag, text) }
        fun w(text: String) { Log.println(Log.WARN, defaultTag, text) }
    }
}