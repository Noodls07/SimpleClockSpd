package com.example.simpleclock.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpleclock.settingsmodel.ClockModel
import com.example.simpleclock.ui.theme.SimpleClockTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownField(model: ClockModel){
    val clockSizeText = listOf("30","36","40","45","50","60","70","80","90","100","120")
//    val selectedText = remember {
//        mutableStateOf(model.clockTextSizeBig.toInt().toString())
//    }
    val expanded = remember {
        mutableStateOf(false)
    }

//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .height(60.dp),
//        //.padding(6.dp),
//        contentAlignment = Alignment.Center
//
//    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {


            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth()
                    .padding(3.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .border(1.dp, Color(0xFFBDBDBD), RoundedCornerShape(5.dp)),
                expanded = expanded.value,
                onExpandedChange = {
                    expanded.value = !expanded.value
                }
            ){
                TextField(
                    value = model.clockTextSizeBig.toInt().toString(),//selectedText.value,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text( text = "Clock font size" )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                    },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    clockSizeText.forEach {
                        DropdownMenuItem(
                            text = { Text ( text = it ) },
                            onClick = {
                                //selectedText.value = it
                                expanded.value = false
                                model.setSize(it.toFloat())
                            }
                        )
                    }
                }
            }
        }



//    }
}

@Preview(showSystemUi = true)
@Composable
fun SowImg(){
    SimpleClockTheme() {
        DropDownField(model = ClockModel())
    }
}