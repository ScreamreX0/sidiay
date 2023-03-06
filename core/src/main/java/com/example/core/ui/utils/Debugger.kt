package com.example.core.ui.utils

import android.util.Log

class Debugger {
    companion object {
        private const val defaultTag = "DEBUG"
        fun printInfo(text: String, tag: String) { Log.println(Log.INFO, tag, text) }
        fun printInfo(text: String?) {
            text?.let { Log.println(Log.INFO, defaultTag, it) } ?: Log.println(Log.INFO, defaultTag, "null")
        }
        fun printError(text: String, tag: String) { Log.println(Log.ERROR, tag, text) }
        fun printError(text: String) { Log.println(Log.ERROR, defaultTag, text) }
        fun printWarn(text: String, tag: String) { Log.println(Log.WARN, tag, text) }
        fun printWarn(text: String) { Log.println(Log.WARN, defaultTag, text) }
    }
}