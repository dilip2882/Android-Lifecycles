package com.dilip.androidlifecyclexml.sharedpreferences

import android.content.SharedPreferences
import com.dilip.androidlifecyclexml.sharedpreferences.SharedPrefsEntry

internal class SharedPrefsEntryInt(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val default: Int
): SharedPrefsEntry<Int> {

    override fun set(value: Int) {
        sharedPreferences.edit().putInt(key, value).commit()
    }

    override fun get(): Int {
        return sharedPreferences.getInt(key, default)
    }
}