package com.example.simpleclock

import android.Manifest
import android.app.Activity
import android.app.PictureInPictureParams
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Rational
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.simpleclock.navigation.Navigation
import com.example.simpleclock.settingsmodel.ClockModel
import com.example.simpleclock.settingsmodel.DataStoreManager
import com.example.simpleclock.ui.theme.SimpleClockTheme

var speed = mutableIntStateOf(0)

class MainActivity : ComponentActivity() {

    private var tmpBigSize = 0F
    private var dataStoreManager = DataStoreManager(this@MainActivity)
    private val model by viewModels<ClockModel>() //- создание модели без параметров
//с параметрами
//    private val model by viewModels<ClockModel>(
//        factoryProducer = {
//            object : ViewModelProvider.Factory {
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return ClockModel(param) as T
//                }
//            }
//        }
//    )

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light( Color.Transparent.toArgb(), Color.Transparent.toArgb())
        )
        super.onCreate(savedInstanceState)
        //dataStoreManager.getSettings()

        setContent {

            requestPermission(context = LocalContext.current)
            SimpleClockTheme() {
                LaunchedEffect(true) {
                   // model.readDataFromStore()
                  dataStoreManager.getSettings().collect {
                        model.clockTextSizeBig = it.clockTextSizeBig
                        model.clockTextSizeSmall = it.clockTextSizeSmall
                        model.r = it.r
                        model.g = it.g
                        model.b = it.b
                        model.gpsCorrection = it.gps
                  }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,

                ) {
                    Navigation(model,dataStoreManager)
                }
            }
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (!model.settingsScreenIsOn) enterPIPMode()
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        model.settingsScreenIsOn = false
//    }



    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        when(isInPictureInPictureMode){
            true -> {
                tmpBigSize = model.clockTextSizeBig
                model.pipOn = true
                model.clockTextSizeBig = 40F
            }
            false -> {
                model.pipOn = false
                model.clockTextSizeBig = tmpBigSize
            }
        }

    }

    private fun enterPIPMode(){
        val aspectRatio = Rational(16,9)
        val params = PictureInPictureParams
            .Builder()
            .setAspectRatio(aspectRatio)
            .build()
        enterPictureInPictureMode(params)
    }
}

fun requestPermission(context : Context){
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
    } else {
        startLocationUpdates(context)
    }
}

fun startLocationUpdates(context : Context) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val locationListener = LocationListener { location ->
        speed.intValue = (location.speed * 3.6).toInt()
    }

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
}




//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SimpleClockTheme {
//        Greeting("Android")
//    }
//}