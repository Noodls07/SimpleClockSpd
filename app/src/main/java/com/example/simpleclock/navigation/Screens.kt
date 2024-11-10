package com.example.simpleclock.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object ClockScreen: Screens
    @Serializable
    data object SettingsScreen: Screens
}