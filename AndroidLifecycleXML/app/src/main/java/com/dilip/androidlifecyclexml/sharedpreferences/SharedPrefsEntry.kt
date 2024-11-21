package com.dilip.androidlifecyclexml.sharedpreferences

interface SharedPrefsEntry<T> {
    fun set(value: T)
    fun get(): T
}