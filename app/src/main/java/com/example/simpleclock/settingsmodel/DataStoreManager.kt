package com.example.simpleclock.settingsmodel

import android.content.Context
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map


val Context.dataStore : DataStore<Preferences> by preferencesDataStore("settings")

class DataStoreManager(private val context: Context) {

    suspend fun saveSettings(clockSettings: ClockModel){
        context.dataStore.edit { pref ->
            pref[floatPreferencesKey("clockTextSizeBig")] = clockSettings.clockTextSizeBig
            pref[floatPreferencesKey("clockTextSizeSmall")] = clockSettings.clockTextSizeSmall
            pref[intPreferencesKey("color_r")] = clockSettings.r
            pref[intPreferencesKey("color_g")] = clockSettings.g
            pref[intPreferencesKey("color_b")] = clockSettings.b
        }
    }

    fun getSettings()= context.dataStore.data.map {pref ->
            return@map DataSettings(
                pref[floatPreferencesKey("clockTextSizeBig")] ?: 60f,
                pref[floatPreferencesKey("clockTextSizeSmall")] ?: 25f,
                pref[intPreferencesKey("color_r")] ?: 11,
                pref[intPreferencesKey("color_g")] ?: 11,
                pref[intPreferencesKey("color_b")] ?: 11
            )
        }

}
