package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

val UNIT_OPTIONS = listOf("Meter", "Centimeter", "Inch", "Feet")

@Composable
fun UnitConverter() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(text = "Unit Converter")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Place enter a number") })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            DropdownBtn("Select", UNIT_OPTIONS)
            Spacer(modifier = Modifier.width(16.dp))
            DropdownBtn("Select", UNIT_OPTIONS)

        }
    }
}

@Composable
fun DropdownBtn(description: String, options: List<String>) {
    Box {
        Button(onClick = { /*TODO*/ }) {
            Text(text = description)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Arrow Down"
            )
            DropdownMenu(expanded = true, onDismissRequest = { /*TODO*/ }) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it) },
                        onClick = { /*TODO*/ })
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewUnitConverter() {
    UnitConverter()
}



