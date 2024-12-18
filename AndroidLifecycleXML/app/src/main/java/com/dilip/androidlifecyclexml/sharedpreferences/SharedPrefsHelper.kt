package com.dilip.androidlifecyclexml.sharedpreferences

import android.content.Context

class SharedPrefsHelper(
    private val context: Context,
) {

    private val sharedPreferences = context.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)

    fun foregroundCount(): SharedPrefsEntry<Int> {
        return SharedPrefsEntryInt(sharedPreferences, KEY_FOREGROUND_COUNT, 0)
    }

    private companion object {
        const val KEY_FOREGROUND_COUNT = "KEY_FOREGROUND_COUNT"
    }
}