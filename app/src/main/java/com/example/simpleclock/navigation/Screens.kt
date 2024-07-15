package com.example.simpleclock.navigation

sealed class Screens(val route: String) {
    object ClockScreen: Screens("clock_screen")
    object SettingsScreen: Screens("settings_screen")
}