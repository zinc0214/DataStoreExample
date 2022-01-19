package com.zinc.datastoreexample.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceDataStoreModule(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

    private val nickNameKey = stringPreferencesKey("nickName")

    val loadNickName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[nickNameKey] ?: ""
        }

    suspend fun setNickName(newNickName: String) {
        context.dataStore.edit { preferences ->
            preferences[nickNameKey] = newNickName
        }
    }
}