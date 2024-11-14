package com.example.simpleclock.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpleclock.screens.ShowClockScreen
import com.example.simpleclock.screens.ShowSettingsScreen
import com.example.simpleclock.settingsmodel.ClockModel
import com.example.simpleclock.settingsmodel.DataStoreManager

@Composable
fun Navigation(model: ClockModel, dataStoreManager: DataStoreManager) {
    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = Screens.ClockScreen.route) {
//        composable(route = Screens.ClockScreen.route){
//            ClockScreen(model = model, navController = navController )
//        }
//        composable(route = Screens.SettingsScreen.route){
//            //SettingsScreen(model = model, navController = navController )
//            ShowSettingsScreen(model = model, navController = navController, dataStoreManager )
//        }
//    }
    NavHost(navController = navController, startDestination = Screens.ClockScreen) {
        composable<Screens.ClockScreen> {
            ShowClockScreen(
                model =  model,
                gotoSettingsScreen = {
                    navController.navigate(Screens.SettingsScreen)
                }
            )
        }
        composable<Screens.SettingsScreen> {
            ShowSettingsScreen(
                model =  model,
                gotoClockScreen = {
                     navController.navigateUp()
                },
                dataStoreManager = dataStoreManager
            )
        }
    }
}