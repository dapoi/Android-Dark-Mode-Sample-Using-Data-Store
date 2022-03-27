package com.dapoi.sampledarkmode

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class SettingDataStore(context: Context) {

    private val appContext = context.applicationContext

    suspend fun setDarkMode(uiTheme: UITheme) {
        appContext.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = when (uiTheme) {
                UITheme.LIGHT -> false
                UITheme.DARK -> true
            }
        }
    }

    val getDarkMode: Flow<UITheme> = appContext.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            when (it[IS_DARK_MODE] ?: false) {
                true -> UITheme.DARK
                false -> UITheme.LIGHT
            }
        }

    companion object {
        private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }
}