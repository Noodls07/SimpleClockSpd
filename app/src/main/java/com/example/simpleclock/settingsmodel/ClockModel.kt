package com.example.simpleclock.settingsmodel

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


//val context = LocalContext.current

class ClockModel() : ViewModel() {



    var clockTextSizeBig by mutableFloatStateOf(30f)
    var clockTextSizeSmall by mutableFloatStateOf(20f)
    var r by mutableIntStateOf(163)
    var g by mutableIntStateOf(163)
    var b by mutableIntStateOf(163)
    var pipOn by mutableStateOf(false)
    var settingsScreenIsOn by mutableStateOf(false)


    fun setSize(size: Float){
        clockTextSizeBig = size
    }

    fun setColor(red : Int, green : Int, blue : Int){
        r = red
        g = green
        b = blue
    }

//    fun readDataFromStore (){
//        viewModelScope.launch {
//            dataStoreManager.getSettings().collect{
//                this@ClockModel.clockTextSizeBig = it.clockTextSizeBig
//                this@ClockModel.clockTextSizeSmall = it.clockTextSizeSmall
//                this@ClockModel.r = it.r
//                this@ClockModel.g = it.g
//                this@ClockModel.b = it.b
//            }
//        }
//
//    }

//    fun saveDataToStore (){
//        viewModelScope.launch {
//            dataStoreManager.saveSettings(this@ClockModel)
//        }
//
//    }
}