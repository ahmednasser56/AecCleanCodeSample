package com.aec.aeccleancodesample.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesUtility @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun setString(key: String, value: String) = sharedPreferences.edit().apply {
        putString(key, value)
        apply()
    }

    fun getString(key: String, defaultValue: String = "") =
        sharedPreferences.getString(key, defaultValue)!!


    fun setInt(key: String, value: Int) = sharedPreferences.edit().apply {
        putInt(key, value)
        apply()
    }

    fun getInt(key: String, defaultValue: Int = -1) =
        sharedPreferences.getInt(key, defaultValue)


    fun setBoolean(key: String, value: Boolean) = sharedPreferences.edit().apply {
        putBoolean(key, value)
        apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false) =
        sharedPreferences.getBoolean(key, defaultValue)


    fun remove(key: String) = sharedPreferences.edit().apply {
        remove(key)
        apply()
    }

    fun clearAllData() = sharedPreferences.edit().apply {
        clear()
        apply()
    }
}