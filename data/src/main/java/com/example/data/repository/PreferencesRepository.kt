package com.example.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class PreferencesRepository(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getTextFlow(key: String): Flow<String> {
        val savedText = sharedPreferences.getString(key, "") ?: ""
        return MutableStateFlow(savedText)
    }

    fun saveText(key: String, text: String) {
        sharedPreferences.edit().putString(key, text).apply()
    }
}