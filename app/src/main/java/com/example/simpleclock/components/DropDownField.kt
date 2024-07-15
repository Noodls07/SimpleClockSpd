package com.example.simpleclock.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simpleclock.screens.SettingsScreen
import com.example.simpleclock.settingsmodel.ClockModel
import com.example.simpleclock.ui.theme.SimpleClockTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownField(model: ClockModel){
    val clockSizeText = listOf("30","36","40","45","50","60","70","80","90","100","120")
    val selectedText = remember {
        mutableStateOf(model.clockTextSizeBig.toInt().toString())
    }
    val expanded = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(6.dp),
        contentAlignment = Alignment.Center

    ){
         ExposedDropdownMenuBox(
             expanded = expanded.value,
             onExpandedChange = {
                 expanded.value = !expanded.value
             }
         ){

            TextField(
                value = selectedText.value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                },
                modifier = Modifier.menuAnchor(),
            )
             ExposedDropdownMenu(
                 expanded = expanded.value,
                 onDismissRequest = { expanded.value = false }
             ) {
                 clockSizeText.forEach {
                     DropdownMenuItem(
                         text = { Text ( text = it ) },
                         onClick = {
                             selectedText.value = it
                             expanded.value = false
                             model.setSize(it.toFloat())
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
        DropDownField(model = ClockModel())
    }
}