package com.example.firealarmtoolforengineers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.firealarmtoolforengineers.ui.theme.FireAlarmToolForEngineersTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FireAlarmToolForEngineersTheme {
                Scaffold(
                    topBar = {TopAppBar(
                            title = {
                                Text("Backup Battery Capacity Calculator")
                            },
                            colors = topAppBarColors(
                                containerColor = Color(0.95f,0.2f,0.2f)
                            )
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.fire_alarm_realistic),
                            contentDescription = "Background",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.alpha(0.1f).fillMaxHeight()
                        )
                        Column (modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = 8.dp)){
                            var standbyTime by remember { mutableStateOf("") }
                            var alarmTime by remember { mutableStateOf("") }
                            var quiescenceCurrent by remember { mutableStateOf("") }
                            var alarmCurrent by remember { mutableStateOf("") }
                            var minBatteryCapacity by remember { mutableStateOf("N/a") }
                            var areInputsInvalid by remember { mutableStateOf(true) }
                            RadioButtonSingleSelection(
                                radioOptions = listOf("24","48","72"),
                                title = "Standby Time (hours)",
                                selectedOption = standbyTime,
                                onOptionSelected = {
                                    standbyTime = it
                                }
                            )
                            SimpleOutlinedNumberField(
                                title = "Quiescence Current (Amps)",
                                numberText = quiescenceCurrent,
                                onNumberSet = {
                                    quiescenceCurrent = it
                                }
                            )
                            RadioButtonSingleSelection(
                                radioOptions = listOf("0.5","1","2"),
                                title = "Alarm Time (hours)",
                                selectedOption = alarmTime,
                                onOptionSelected = {
                                    alarmTime = it
                                }
                            )
                            SimpleOutlinedNumberField(
                                title = "Alarm Current (Amps)",
                                numberText = alarmCurrent,
                                onNumberSet = {
                                    alarmCurrent = it
                                }
                            )
                            OutlinedButton(onClick = {
                                areInputsInvalid = standbyTime.isEmpty() or quiescenceCurrent.isEmpty() or alarmTime.isEmpty() or alarmCurrent.isEmpty()
                                if (!areInputsInvalid){
                                    minBatteryCapacity = "%.2f".format(1.25 * (standbyTime.toDouble()*quiescenceCurrent.toDouble() + 1.75*alarmTime.toDouble()*alarmCurrent.toDouble() ) )
                                }
                            }){
                                Text("Calculate")
                            }


                            Column{
                                Text("Minimum Battery Capacity: $minBatteryCapacity")
                                Text(
                                    "Recommended Battery Size: " + {
                                        var bsr = batterySizeRecommendation(minBatteryCapacity.toDoubleOrNull())
                                        if (bsr == null){
                                            "N/a"
                                        } else {
                                            "Recommended Battery Size: " + bsr.toString() + "Ah"
                                        }
                                    }()
                                )
                            }


                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RadioButtonSingleSelection(selectedOption: String,onOptionSelected : (String)->Unit,radioOptions: List<String>, modifier: Modifier = Modifier, title: String = "") {
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(
        modifier
    ) {
        Text(
            text = title,
            //style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
        Row(Modifier
            .selectableGroup()
            .fillMaxWidth()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .weight(1F)
                        .height(48.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null // null recommended for accessibility with screen readers
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleOutlinedNumberField(numberText: String, onNumberSet: (String)->Unit, title: String = "") {
    val pattern = remember { Regex("^\\d*(\\.\\d*)?") }

    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = numberText,
            onValueChange = {
                if (it.isEmpty() || it.matches(pattern)) {
                    onNumberSet(it)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
    }
}

