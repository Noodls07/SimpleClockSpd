package com.example.simpleclock.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.simpleclock.settingsmodel.ClockModel
import com.example.simpleclock.settingsmodel.DataStoreManager
import com.example.simpleclock.ui.theme.SimpleClockTheme

@Composable
fun DownText(){

    var expanded by remember {
        mutableStateOf(false)
    }

    val txt = "Lorem Ipsum - это текст-\"рыба\", часто используемый в печати и вэб-дизайне. Lorem Ipsum является стандартной \"рыбой\" для текстов на латинице с начала XVI века. В то время некий безымянный печатник создал большую коллекцию размеров и форм шрифтов, используя Lorem Ipsum для распечатки образцов. Lorem Ipsum не только успешно пережил без заметных изменений пять веков, но и перешагнул в электронный дизайн. Его популяризации в "

    Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween


            ) {

                Text(
                    text = "Label",
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { expanded = !expanded },
                ) {
                    Icon(imageVector = if (!expanded) Icons.Rounded.KeyboardArrowDown else Icons.Rounded.KeyboardArrowUp, contentDescription = "")
                }
            }
            Card(
                modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
                border = BorderStroke(1.dp, Color.Red)

            ) {
                if (!expanded)
                    Text(modifier = Modifier.padding(5.dp),text = txt, maxLines = 3, overflow = TextOverflow.Ellipsis)
                else
                    Text(modifier = Modifier.padding(5.dp),text = txt)
            }

        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "aaaaa")
    }


}

@Preview(showSystemUi = true)
@Composable
fun DropDownText(){
    SimpleClockTheme {
        DownText()
    }
}