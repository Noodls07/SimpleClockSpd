package com.example.simpleclock.screens


import android.widget.TextClock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.simpleclock.R
import com.example.simpleclock.WindowType
import com.example.simpleclock.rememberWindowSize
import com.example.simpleclock.settingsmodel.ClockModel
import com.example.simpleclock.speed
import com.example.simpleclock.ui.theme.SimpleClockTheme





@Composable
fun ShowClockScreen(
    model: ClockModel,
    gotoSettingsScreen:()->Unit
){
    val windowSize = rememberWindowSize()

    when (windowSize.width) {
        WindowType.Compact -> ClockScreen(
            model,
            gotoSettingsScreen
        )
        else -> ClockScreenElse(
            model,
            gotoSettingsScreen
        )
    }
}

@Composable
fun ClockScreen(
    model: ClockModel,
    gotoSettingsScreen:()->Unit
) {
    //val contextM = LocalContext.current
    Scaffold(
        floatingActionButton = {
            if (!model.pipOn){
                FloatingActionButton(
                    onClick = {
                        model.settingsScreenIsOn = true
                        gotoSettingsScreen()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Build ,
                        contentDescription = "Settings",
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {paddingValues ->

        Column(
            modifier = Modifier
                .background(Color(model.r, model.g, model.b))
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            AndroidView(

                factory = {context ->
                TextClock(context).apply {
                    format24Hour?.let {
                       this.format24Hour = "HH : mm"
                    }
                    textSize.let {
                        this.textSize = model.clockTextSizeBig
                    }
                    typeface =  resources.getFont(R.font.anton)
                    setTextColor(context.getColor(R.color.Green))
                }
            },
                update = {
                    it.textSize = model.clockTextSizeBig
                }
            )
            if (!model.pipOn) {
                AndroidView(factory = { context ->
                    TextClock(context).apply {
                        format24Hour?.let {
                            this.format24Hour = "dd/MM/yyyy"
                        }
                        textSize.let {
                            this.textSize = 20F//model.clockTextSizeSmall
                        }
                        setTextColor(context.getColor(R.color.white))
                    }
                }
                )
            }
            if (!model.pipOn) Spacer(modifier = Modifier.size(20.dp))

            Row (horizontalArrangement = Arrangement.Center){
                Text(
                    text = if ( speed.intValue > 0) (speed.intValue + model.gpsCorrection).toString()
                           else speed.intValue.toString(),
                    fontFamily =  FontFamily(
                        Font(R.font.sairasemicondensed_bold),
                        ),
                    color = if ((speed.intValue >= 0) and (speed.intValue + model.gpsCorrection<=120)) Color(0xFFFFFFFF)
                            else Color(0xFFF57F17),
                    fontSize = if (!model.pipOn) 60.sp else 30.sp,
                    textAlign = TextAlign.Center,

                )
                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "км/ч",
                    fontFamily = FontFamily(
                        Font(R.font.sairasemicondensed_bold),
                    ),
                    fontSize = if (!model.pipOn) 60.sp else 30.sp,
                    //textAlign = TextAlign.Center,
                    color = Color(0xFF7986CB)

                )

            }
        }

    }
}

@Composable
fun ClockScreenElse(
    model: ClockModel,
    gotoSettingsScreen:()->Unit
) {
    //val contextM = LocalContext.current
    Scaffold(
        floatingActionButton = {
            if (!model.pipOn){
                FloatingActionButton(
                    onClick = {
                        model.settingsScreenIsOn = true
                        gotoSettingsScreen()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Build ,
                        contentDescription = "Settings",
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {paddingValues ->

        Row(
            modifier = Modifier
                .background(Color(model.r, model.g, model.b))
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment =  Alignment.CenterHorizontally
            ) {
                AndroidView(

                    factory = {context ->
                        TextClock(context).apply {
                            format24Hour?.let {
                                this.format24Hour = "HH : mm"
                            }
                            textSize.let {
                                this.textSize = model.clockTextSizeBig
                            }
                            typeface =  resources.getFont(R.font.anton)//chivo_regular
                            setTextColor( context.getColor(R.color.Green))
                        }
                    },
                    update = {
                        it.textSize = model.clockTextSizeBig
                    }
                )
                if (!model.pipOn) {
                    AndroidView(factory = { context ->
                        TextClock(context).apply {
                            format24Hour?.let {
                                this.format24Hour = "dd/MM/yyyy"
                            }
                            textSize.let {
                                this.textSize = 20F//model.clockTextSizeSmall
                            }
                            setTextColor(context.getColor(R.color.white))
                        }
                    }
                    )
                }
            }
            VerticalDivider(modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .height(((model.clockTextSizeBig + model.clockTextSizeSmall)*1.2).dp),
                thickness = 3.dp,
                color = Color.White)

            //Spacer(modifier = Modifier.width(20.dp))
            //if (!model.pipOn) Spacer(modifier = Modifier.size(20.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = if ( speed.intValue > 0) (speed.intValue + model.gpsCorrection).toString()
                           else speed.intValue.toString(),
                    fontFamily =  FontFamily(
                        Font(R.font.sairasemicondensed_bold),//sairasemicondensed_bold
                    ),
                    color = if ((speed.intValue >= 0) and (speed.intValue + model.gpsCorrection<=120)) Color(0xFFFFFFFF)
                            else Color(0xFFF57F17),
                    fontSize = if (!model.pipOn) 65.sp else 30.sp
                    )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "км/ч",
                    fontFamily = FontFamily(
                        Font(R.font.sairasemicondensed_bold),
                    ),
                    fontSize = if (!model.pipOn) 40.sp else 30.sp,
                    color = Color(0xFF7986CB)
                )

            }
        }

    }
}

@Preview(showBackground = true,
    showSystemUi = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
    )
@Composable
fun ClockPreview() {
    SimpleClockTheme {

        ClockScreenElse(
            model = ClockModel(),
            //NavController(LocalContext.current.applicationContext)
            gotoSettingsScreen = { }
        )
    }
}