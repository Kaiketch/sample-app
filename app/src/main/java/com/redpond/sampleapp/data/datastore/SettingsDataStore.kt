package com.redpond.sampleapp.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val SHOULD_USER_CACHE = booleanPreferencesKey("should_user_cache")


    val shouldUserCache: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SHOULD_USER_CACHE] ?: true
        }

    suspend fun setShouldUserCache(shouldUserCache: Boolean) {
        context.dataStore.edit { settings ->
            settings[SHOULD_USER_CACHE] = shouldUserCache
        }
    }
}