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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
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
                    UnitConverter(UnitConverterViewModel())
                }
            }
        }
    }
}

enum class UNIT(val factor: Double) {
    Select(-1.0),
    Meter(100.0),
    Centimeter(1.0),
    Inch(2.54),
    Feet(30.48),
}

class UnitConverterViewModel : ViewModel() {
    var inputUnit by  mutableStateOf(UNIT.Select)
    var outputUnit by mutableStateOf(UNIT.Select)
    var inputValue by mutableStateOf("")
    var resultValue by mutableStateOf("")

    private fun convert() {
        val input = inputValue.toDoubleOrNull() ?: 0.0
        if (inputUnit != UNIT.Select && outputUnit != UNIT.Select) {
            val result = (input * inputUnit.factor * 100 / outputUnit.factor) / 100
            resultValue = result.toString()
        }
    }

    fun updateInputUnit(unit: UNIT) {
        inputUnit = unit
        convert()
    }

    fun updateOutputUnit(unit: UNIT) {
        outputUnit = unit
        convert()
    }

    fun updateInputValue(value: String) {
        inputValue = value
        convert()
    }
}


@Composable
fun UnitConverter(viewModel: UnitConverterViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(text = "Unit Converter")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(modifier = Modifier
                .width(150.dp)
                .height(60.dp),
                value = viewModel.inputValue,
                label = { Text(text = "Enter Value") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { viewModel.updateInputValue(it) })
            Spacer(modifier = Modifier.width(16.dp))
            DropdownBtn(viewModel.inputUnit) {
                viewModel.updateInputUnit(it)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .width(150.dp)
                    .height(60.dp),
                value = viewModel.resultValue,
                onValueChange = {},
                enabled = false
            )
            Spacer(modifier = Modifier.width(16.dp))
            DropdownBtn(viewModel.outputUnit) {
                viewModel.updateOutputUnit(it)
            }
        }
    }
}

@Composable
fun DropdownBtn(selectedUnit: UNIT, onSelect: (UNIT) -> Unit) {
    var expand by remember {
        mutableStateOf(false)
    }
    Box {
        Button(onClick = { expand = true }, modifier = Modifier.width(120.dp)) {
            Text(text = selectedUnit.name)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Arrow Down"
            )
        }
        DropdownMenu(expanded = expand, onDismissRequest = { expand = false }) {
            UNIT.entries.forEach {
                if (it != UNIT.Select) {
                    DropdownMenuItem(
                        text = { Text(text = it.toString()) },
                        onClick = {
                            onSelect(it)
                            expand = false
                        })
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewUnitConverter() {
    UnitConverter(UnitConverterViewModel())
}



