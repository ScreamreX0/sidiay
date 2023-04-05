package com.example.core.utils

import android.util.Log

class Logger {
    companion object {
        private const val defaultTag = "DEBUG"
        fun log(vararg text: String?) {
            text.forEach {
                it?.let {
                    Log.println(Log.INFO, defaultTag, it)
                } ?: Log.println(Log.INFO, defaultTag, "null")
            }
        }
        fun logErr(text: String, tag: String) { Log.println(Log.ERROR, tag, text) }
        fun logErr(text: String) { Log.println(Log.ERROR, defaultTag, text) }
        fun logWarn(text: String, tag: String) { Log.println(Log.WARN, tag, text) }
        fun logWarn(text: String) { Log.println(Log.WARN, defaultTag, text) }
    }
}