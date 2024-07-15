package com.example.simpleclock.screens


import android.widget.TextClock
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.simpleclock.R
import com.example.simpleclock.WindowType
import com.example.simpleclock.components.DropDownField
import com.example.simpleclock.navigation.Screens
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
    navController: NavController,
    dataStoreManager: DataStoreManager
){
    val windowSize = rememberWindowSize()

    when (windowSize.width) {
        WindowType.Compact -> SettingsScreen(model, navController,dataStoreManager)
        else -> SettingsScreenElse(model, navController,dataStoreManager)
    }
}






@Composable
fun SettingsScreen(model: ClockModel, navController: NavController, dataStoreManager: DataStoreManager) {
    //val context = LocalContext.current
    val imageBitmap: ImageBitmap = ImageBitmap.imageResource(
        LocalContext.current.resources,
        R.drawable.img
    )
    val bitmapWidth = imageBitmap.width
    val bitmapHeight = imageBitmap.height
    var imageSize by remember { mutableStateOf(Size.Zero) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    model.settingsScreenIsOn = false
                    //model.saveDataToStore()
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStoreManager.saveSettings(model)
                    }
                    navController.navigate(Screens.ClockScreen.route){
                        popUpTo(Screens.ClockScreen.route){
                            inclusive = true
                        }
                    }
                }
            )
            {
                Icon(imageVector = Icons.Rounded.Check, contentDescription ="" )
            }
        }
    ) {paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.LightGray),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            DropDownField(model)
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    //.rotate(90F)
                    .clip(RoundedCornerShape(6.dp))
                    .pointerInput(false) {
                        detectTapGestures { offset: Offset ->
                            // Touch coordinates on image
                            val offsetX = offset.x
                            val offsetY = offset.y
                            // Scale from Image touch coordinates to range in Bitmap
                            val scaledX = (bitmapWidth / imageSize.width) * offsetX
                            val scaledY = (bitmapHeight / imageSize.height) * offsetY
                            try {
                                val pixel: Int = imageBitmap
                                    .asAndroidBitmap()
                                    .getPixel(scaledX.toInt(), scaledY.toInt())
                                // Don't know if there is a Compose counterpart for this
                                val red = android.graphics.Color.red(pixel)
                                val green = android.graphics.Color.green(pixel)
                                val blue = android.graphics.Color.blue(pixel)

                                model.setColor(red, green, blue)


                            } catch (e: Exception) {
                                println("Exception e: ${e.message}")
                            }
                        }
                    }
                    .onSizeChanged { imageSize = it.toSize() }

            )
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
        }

    }

}


@Composable
fun SettingsScreenElse(model: ClockModel, navController: NavController, dataStoreManager: DataStoreManager) {

    val imageBitmap: ImageBitmap = ImageBitmap.imageResource(
        LocalContext.current.resources,
        R.drawable.img
    )
    val bitmapWidth = imageBitmap.width
    val bitmapHeight = imageBitmap.height
    var imageSize by remember { mutableStateOf(Size.Zero) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    model.settingsScreenIsOn = false
                    //model.saveDataToStore()
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStoreManager.saveSettings(model)
                    }
                    navController.popBackStack() //navigate(Screens.ClockScreen.route)
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
            .background(Color.LightGray),
            verticalAlignment =  Alignment.CenterVertically
        )
        {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxSize(0.8F)
                        //.height(500.dp)
                        //.rotate(90F)
                        .clip(RoundedCornerShape(6.dp))
                        .pointerInput(false) {
                            detectTapGestures { offset: Offset ->
                                // Touch coordinates on image
                                val offsetX = offset.x
                                val offsetY = offset.y
                                // Scale from Image touch coordinates to range in Bitmap
                                val scaledX = (bitmapWidth / imageSize.width) * offsetX
                                val scaledY = (bitmapHeight / imageSize.height) * offsetY
                                try {
                                    val pixel: Int = imageBitmap
                                        .asAndroidBitmap()
                                        .getPixel(scaledX.toInt(), scaledY.toInt())
                                    // Don't know if there is a Compose counterpart for this
                                    val red = android.graphics.Color.red(pixel)
                                    val green = android.graphics.Color.green(pixel)
                                    val blue = android.graphics.Color.blue(pixel)

                                    model.setColor(red, green, blue)


                                } catch (e: Exception) {
                                    println("Exception e: ${e.message}")
                                }
                            }
                        }
                        .onSizeChanged { imageSize = it.toSize() }

                )

            Spacer(modifier = Modifier.height(10.dp))

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),

                verticalArrangement = Arrangement.Top,)
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
            }
            }




    }

}


@Preview(showSystemUi = true)
@Composable
fun SowImg(){
    SimpleClockTheme() {
        SettingsScreen(model = ClockModel(),NavController(LocalContext.current.applicationContext), dataStoreManager = DataStoreManager(LocalContext.current.applicationContext))
        //SettingsScreenElse(model = ClockModel(LocalContext.current.applicationContext),NavController(LocalContext.current.applicationContext))
    }
}