package com.dokari4.personalfinance.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.dokari4.personalfinance.util.OnboardingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
class DataStoreManager(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "onboarding")

    private val dataStore = context.dataStore

    val checkOnboardingState: Flow<OnboardingState> = dataStore.data.map { preferences ->
        when (preferences[ONBOARDING_KEY] ?: false) {
            true -> OnboardingState.DONE
            false -> OnboardingState.NOT_DONE
        }
    }

    suspend fun setOnboardingState(onboardingState: OnboardingState) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_KEY] = when (onboardingState) {
                OnboardingState.DONE -> true
                OnboardingState.NOT_DONE -> false
            }
        }
    }

    companion object {
        val ONBOARDING_KEY = booleanPreferencesKey("onboarding_key")
    }
}