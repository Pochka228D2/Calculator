package com.example.zadanie1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zadanie1.ui.theme.Blue
import com.example.zadanie1.ui.theme.Green
import com.example.zadanie1.ui.theme.Purple40
import com.example.zadanie1.ui.theme.Purple80

@Composable
fun Calculator(navController: NavController) {
    val blushh = Brush.verticalGradient(listOf(Green, Blue))
    val kekk = FontFamily(Font(R.font.mmm))

    // Градиент для кнопки "КАЛЬКУЛЯТОР"
    val gradientButton = Brush.horizontalGradient(listOf(Purple40, Purple80))


    val gradientCalculate = Brush.horizontalGradient(
        listOf(Color(0xFF2196F3), Color(0xFF03A9F4), Color(0xFF00BCD4))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(blushh),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                ConverterItem(
                    leftLabel = "градусы",
                    rightLabel = "радианы",
                    convert = { value -> value * Math.PI / 180 },
                    gradient = gradientCalculate
                )


                ConverterItem(
                    leftLabel = "километры",
                    rightLabel = "мили",
                    convert = { value -> value * 0.62 },
                    gradient = gradientCalculate
                )


                ConverterItem(
                    leftLabel = "метры",
                    rightLabel = "футы",
                    convert = { value -> value * 3.28 },
                    gradient = gradientCalculate
                )


                HexConverterItem(gradient = gradientCalculate)


                BinaryConverterItem(gradient = gradientCalculate)


            }
        }
    }
}

@Composable
fun GradientButton(
    onClick: () -> Unit,
    text: String,
    gradient: Brush,
    modifier: Modifier = Modifier,
    fontSize: androidx.compose.ui.unit.TextUnit = 14.sp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(gradient)
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp),  // Уменьшен вертикальный отступ
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun ConverterItem(
    leftLabel: String,
    rightLabel: String,
    convert: (Double) -> Double,
    gradient: Brush
) {
    var leftValue by remember { mutableStateOf("") }
    var rightValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextField(
                value = leftValue,
                onValueChange = { leftValue = it },
                label = leftLabel,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "→",
                fontSize = 28.sp,
                color = Color(0xFF4CAF50),
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            CustomTextField(
                value = rightValue,
                onValueChange = { rightValue = it },
                label = rightLabel,
                modifier = Modifier.weight(1f)
            )
        }


        GradientButton(
            onClick = {
                val number = leftValue.toDoubleOrNull()
                if (number != null) {
                    val result = convert(number)
                    rightValue = if (result % 1 == 0.0) {
                        result.toInt().toString()
                    } else {
                        "%.4f".format(result).trimEnd('0').trimEnd('.')
                    }
                }
            },
            text = "РАССЧИТАТЬ",
            gradient = gradient,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            ),
            placeholder = {
                Text(
                    text = "0",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFF4CAF50),
                unfocusedIndicatorColor = Color.LightGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            singleLine = true
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun HexConverterItem(gradient: Brush) {
    var decimalValue by remember { mutableStateOf("") }
    var hexValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextField(
                value = decimalValue,
                onValueChange = { newValue ->
                    decimalValue = newValue
                    val number = newValue.toIntOrNull()
                    hexValue = if (number != null) {
                        Integer.toHexString(number).uppercase()
                    } else {
                        ""
                    }
                },
                label = "десят.",
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "→",
                fontSize = 28.sp,
                color = Color(0xFF4CAF50),
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            CustomTextField(
                value = hexValue,
                onValueChange = {},
                label = "шестнадцать",
                modifier = Modifier.weight(1f)
            )
        }


        GradientButton(
            onClick = {
                val number = decimalValue.toIntOrNull()
                if (number != null) {
                    hexValue = Integer.toHexString(number).uppercase()
                }
            },
            text = "РАССЧИТАТЬ",
            gradient = gradient,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BinaryConverterItem(gradient: Brush) {
    var decimalValue by remember { mutableStateOf("") }
    var binaryValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextField(
                value = decimalValue,
                onValueChange = { newValue ->
                    decimalValue = newValue
                    val number = newValue.toIntOrNull()
                    binaryValue = if (number != null) {
                        Integer.toBinaryString(number)
                    } else {
                        ""
                    }
                },
                label = "десят.",
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "→",
                fontSize = 28.sp,
                color = Color(0xFF4CAF50),
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            CustomTextField(
                value = binaryValue,
                onValueChange = {},
                label = "двоич.",
                modifier = Modifier.weight(1f)
            )
        }

        GradientButton(
            onClick = {
                val number = decimalValue.toIntOrNull()
                if (number != null) {
                    binaryValue = Integer.toBinaryString(number)
                }
            },
            text = "РАССЧИТАТЬ",
            gradient = gradient,
            modifier = Modifier.fillMaxWidth()
        )
    }
}