package com.example.simpleclock.settingsmodel

data class DataSettings(
    var clockTextSizeBig: Float = 60F,
    var clockTextSizeSmall: Float = 25F,
    var r: Int = 11,
    var g: Int = 11,
    var b: Int = 11,
    var gps: Int = 0
)