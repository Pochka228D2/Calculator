import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zadanie1.ui.theme.Blue
import com.example.zadanie1.ui.theme.Green

@Composable
fun CalculatorMain(navController: NavController) {
    val blushh = Brush.verticalGradient(listOf(Green, Blue))
    var display by rememberSaveable { mutableStateOf("0") }
    var firstNumber by rememberSaveable { mutableStateOf<Double?>(null) }
    var operation by rememberSaveable { mutableStateOf<String?>(null) }
    var waitingForSecondNumber by rememberSaveable { mutableStateOf(false) }

    fun calculateResult() {
        val secondNumber = display.toDoubleOrNull()
        if (firstNumber != null && operation != null && secondNumber != null) {
            val result = when (operation) {
                "+" -> firstNumber!! + secondNumber
                "-" -> firstNumber!! - secondNumber
                "×" -> firstNumber!! * secondNumber
                "/" -> if (secondNumber != 0.0) firstNumber!! / secondNumber else Double.NaN
                else -> secondNumber
            }
            display = if (result.isNaN()) "Ошибка" else {
                if (result % 1 == 0.0) result.toLong().toString()
                else "%.4f".format(result).replace(",", ".").trimEnd('0').trimEnd('.')
            }
            firstNumber = null; operation = null; waitingForSecondNumber = false
        }
    }

    fun onDigitClick(digit: String) {
        if (waitingForSecondNumber) { display = digit; waitingForSecondNumber = false }
        else { display = if (display == "0") digit else display + digit }
    }

    Box(modifier = Modifier.fillMaxSize().background(blushh), contentAlignment = Alignment.Center) {
        Card(modifier = Modifier.fillMaxSize().padding(24.dp), shape = RoundedCornerShape(32.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.fillMaxSize().padding(20.dp), Arrangement.SpaceBetween) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color(0xFFF8F9FA), RoundedCornerShape(16.dp)), contentAlignment = Alignment.BottomEnd) {
                    Text(display, fontSize = 56.sp, modifier = Modifier.padding(16.dp), fontFamily = FontFamily.Monospace)
                }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(10.dp)) {
                        CalculatorButton("+", Color(0xFFE8F5E9), Color(0xFF4CAF50)) { operation = "+"; firstNumber = display.toDoubleOrNull(); waitingForSecondNumber = true }
                        CalculatorButton("-", Color(0xFFE8F5E9), Color(0xFF4CAF50)) { operation = "-"; firstNumber = display.toDoubleOrNull(); waitingForSecondNumber = true }
                        CalculatorButton("×", Color(0xFFE8F5E9), Color(0xFF4CAF50)) { operation = "×"; firstNumber = display.toDoubleOrNull(); waitingForSecondNumber = true }
                        CalculatorButton("/", Color(0xFFE8F5E9), Color(0xFF4CAF50)) { operation = "/"; firstNumber = display.toDoubleOrNull(); waitingForSecondNumber = true }
                    }
                    Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(10.dp)) {
                        CalculatorButton("7") { onDigitClick("7") }
                        CalculatorButton("8") { onDigitClick("8") }
                        CalculatorButton("9") { onDigitClick("9") }
                        CalculatorButton("C", Color(0xFFFFEBEE), Color(0xFFFF5252)) { display = "0"; firstNumber = null; operation = null }
                    }
                    Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(10.dp)) {
                        CalculatorButton("4") { onDigitClick("4") }
                        CalculatorButton("5") { onDigitClick("5") }
                        CalculatorButton("6") { onDigitClick("6") }
                        CalculatorButton("=", Color(0xFFE3F2FD), Color(0xFF2196F3)) { calculateResult() }
                    }
                    Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(10.dp)) {
                        CalculatorButton("1") { onDigitClick("1") }
                        CalculatorButton("2") { onDigitClick("2") }
                        CalculatorButton("3") { onDigitClick("3") }
                        CalculatorButton("0") { onDigitClick("0") }
                    }
                    Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(10.dp)) {
                        CalculatorButton(".", weight = 1f) { if (!display.contains(".")) onDigitClick(".") }
                        CalculatorButton("meon", Color(0xFFFFCC80), weight = 3f) {
                            display = if (display.length > 1) display.dropLast(1) else "0"
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.CalculatorButton(text: String, containerColor: Color = Color(0xFFF5F5F5), contentColor: Color = Color.Black, weight: Float = 1f, onClick: () -> Unit) {
    Box(modifier = Modifier.weight(weight).aspectRatio(weight).clip(RoundedCornerShape(16.dp)).background(containerColor).clickable { onClick() }, contentAlignment = Alignment.Center) {
        Text(text, fontSize = 26.sp, color = contentColor)
    }
}