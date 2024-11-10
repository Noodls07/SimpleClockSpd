package com.example.simpleclock.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.simpleclock.R
import com.example.simpleclock.settingsmodel.ClockModel

@Composable
fun ColorImage(model: ClockModel, height:Float = 1F, width: Float = 1F){

    val imageBitmap: ImageBitmap = ImageBitmap.imageResource(
        LocalContext.current.resources,
        R.drawable.img
    )
    val bitmapWidth = imageBitmap.width
    val bitmapHeight = imageBitmap.height
    var imageSize by remember { mutableStateOf(Size.Zero) }


    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxHeight(height)
            .fillMaxWidth(width)
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
}