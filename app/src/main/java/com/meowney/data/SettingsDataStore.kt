package com.meowney.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
class SettingsDataStore(context: Context) {
    private val appContext = context.applicationContext

    companion object {
        val THEME_OVERLAY_KEY = intPreferencesKey("themeOverlay")
        val NIGHT_MODE_KEY = intPreferencesKey("nightMode")
        val PRIVACY_MODE_KEY = booleanPreferencesKey("privacyMode")
    }

    val themeOverlay: Flow<Int> = appContext.dataStore.data.map { preferences ->
        preferences[THEME_OVERLAY_KEY] ?: 0
    }

    suspend fun saveThemeOverlay(overlayId: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[THEME_OVERLAY_KEY] = overlayId
        }
    }

    val nightMode: Flow<Int> = appContext.dataStore.data.map { preferences ->
        preferences[NIGHT_MODE_KEY] ?: 0
    }

    suspend fun saveNightMode(nightMode: Int) {
        appContext.dataStore.edit { preferences ->
            preferences[NIGHT_MODE_KEY] = nightMode
        }
    }

    val privacyMode: Flow<Boolean> = appContext.dataStore.data.map { preferences ->
        preferences[PRIVACY_MODE_KEY] ?: true
    }

    suspend fun savePrivacyMode(privacyMode: Boolean) {
        appContext.dataStore.edit { preferences ->
            preferences[PRIVACY_MODE_KEY] = privacyMode
        }
    }
}