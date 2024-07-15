package com.example.simpleclock.settingsmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

data class DataSettings (
    var clockTextSizeBig : Float = 60F,
    var clockTextSizeSmall : Float = 25F,
    var r : Int = 11,
    var g : Int = 11,
    var b : Int = 11
)