package com.example.simpleclock.components


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.simpleclock.ui.theme.SimpleClockTheme

@Composable
fun SliderGPS(
    value: Int,
    onGpsCorrection: (Int) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(value.toFloat()) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onGpsCorrection(it.toInt())
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = 4,
            valueRange = 0f..5f,

        )
        Text(text = "+ " + sliderPosition.toInt().toString() +  " км/ч",
            fontSize = 20.sp,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )
    }
}



@Preview(showSystemUi = true)
@Composable
fun ShowSlider(){
    SimpleClockTheme() {
        SliderGPS(
            0,
            {  })
    }
}