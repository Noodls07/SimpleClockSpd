package com.example.simpleclock.screens


import android.widget.TextClock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.simpleclock.R
import com.example.simpleclock.WindowType
import com.example.simpleclock.components.ColorImage
import com.example.simpleclock.components.DropDownField
import com.example.simpleclock.components.SliderGPS
import com.example.simpleclock.rememberWindowSize
import com.example.simpleclock.settingsmodel.ClockModel
import com.example.simpleclock.settingsmodel.DataStoreManager
import com.example.simpleclock.ui.theme.SimpleClockTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ShowSettingsScreen(
    model: ClockModel,
    gotoClockScreen:()->Unit,
    dataStoreManager: DataStoreManager
){
    val windowSize = rememberWindowSize()

    when (windowSize.width) {
        WindowType.Compact -> SettingsScreen(
            model,
            gotoClockScreen,
            dataStoreManager
        )
        else -> SettingsScreenElse(
            model,
            gotoClockScreen,
            dataStoreManager
        )
    }
}



@Composable
fun SettingsScreen(
    model: ClockModel,
    gotoClockScreen:()->Unit,
    dataStoreManager: DataStoreManager
) {
    //val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    model.settingsScreenIsOn = false
                    //model.saveDataToStore()
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStoreManager.saveSettings(model)
                    }
                    gotoClockScreen()
                }
            )
            {
                Icon(imageVector = Icons.Rounded.Check, contentDescription ="" )
            }
        }
    ) {paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            //.verticalScroll(rememberScrollState())
            //.background(Color.LightGray)
            .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            DropDownField(model)

            ColorImage(model, height = 0.6F)

            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "Clock preview"
            )
            Box (modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color(model.r, model.g, model.b))
                , contentAlignment = Alignment.Center
            ){
                AndroidView(

                    factory = {context ->
                        TextClock(context).apply {
                            format24Hour?.let {
                                this.format24Hour = "HH : mm"
                            }
                            textSize.let {
                                this.textSize = 30F
                            }
                            typeface =  resources.getFont(R.font.russoone_regular)//Typeface.DEFAULT_BOLD
                            setTextColor(context.getColor(R.color.Green))
                        }
                    }
                )
            }
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "GPS correction"
            )
            SliderGPS(
                value =  model.gpsCorrection,
                onGpsCorrection = {
                    model.gpsCorrection = it
                }
            )
        }

    }

}


@Composable
fun SettingsScreenElse(
    model: ClockModel,
    gotoClockScreen:()->Unit,
    dataStoreManager: DataStoreManager
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    model.settingsScreenIsOn = false
                    //model.saveDataToStore()
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStoreManager.saveSettings(model)
                    }
                    gotoClockScreen()
                }
            )
            {
                Icon(imageVector = Icons.Rounded.Check, contentDescription ="" )
            }
        }
    ) {paddingValues ->

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            //.background(Color.LightGray)
            ,
            verticalAlignment =  Alignment.CenterVertically
        )
        {
            ColorImage(model, width = 0.7F)

            Spacer(modifier = Modifier.height(10.dp))

            Column(modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.Top)
            {
                DropDownField(model)

                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "Clock preview",
                    color = Color.Black
                )
                Box (modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(model.r, model.g, model.b))
                    , contentAlignment = Alignment.Center
                ){
                    AndroidView(

                        factory = {context ->
                            TextClock(context).apply {
                                format24Hour?.let {
                                    this.format24Hour = "HH : mm"
                                }
                                textSize.let {
                                    this.textSize = 30F
                                }
                                typeface =  resources.getFont(R.font.russoone_regular)//Typeface.DEFAULT_BOLD
                                setTextColor(context.getColor(R.color.lime))

                            }
                        }
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "GPS correction",
                    color = Color.Black
                )
                SliderGPS(
                    value =  model.gpsCorrection,
                    onGpsCorrection = {
                        model.gpsCorrection = it
                    }
                )
            }
        }




    }

}


@Preview(showSystemUi = true,

    //device = Devices.TABLET
)
@Composable
fun SowImg(){
    SimpleClockTheme() {
        SettingsScreen(
            model = ClockModel(),
            //NavController(LocalContext.current.applicationContext),
            gotoClockScreen = {},
            dataStoreManager = DataStoreManager(LocalContext.current.applicationContext)
        )
        //SettingsScreenElse(model = ClockModel(LocalContext.current.applicationContext),NavController(LocalContext.current.applicationContext))
    }
}