package com.example.zadanie1.ui.theme

import android.os.Parcelable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Product(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val article: String,
    val quantity: String,
    val discount: String
)

@Composable
fun ProductListScreen(navController: NavController) {
    var name by rememberSaveable { mutableStateOf("") }
    var article by rememberSaveable { mutableStateOf("") }
    var quantity by rememberSaveable { mutableStateOf("") }
    var discount by rememberSaveable { mutableStateOf("") }

    // Кастомный способ сохранения списка при повороте экрана без Parcelize
    val productSaver = listSaver<MutableState<List<Product>>, List<Product>>(
        save = { listOf(it.value) },
        restore = { mutableStateOf(it[0]) }
    )

    val productsState = rememberSaveable(saver = productSaver) {
        mutableStateOf(listOf<Product>())
    }
    var products by productsState

    // Дальше весь код Column, Button и LazyColumn остается ТАКИМ ЖЕ
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).statusBarsPadding()) {
        Text("Список товаров", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Название") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = article, onValueChange = { article = it }, label = { Text("Артикул") }, modifier = Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("Количество") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = discount, onValueChange = { discount = it }, label = { Text("Скидка") }, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    products = products + Product(name = name, article = article, quantity = quantity, discount = discount)
                    name = ""; article = ""; quantity = ""; discount = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Добавить")
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(products) { item ->
                ProductCard(product = item, onDelete = { products = products.filter { it.id != item.id } })
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(product.article, color = Color.Gray)
                    IconButton(onClick = onDelete, modifier = Modifier.size(30.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Удалить", tint = Color.Black)
                    }
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${product.quantity} шт.")
                Text("${product.discount}%")
            }
        }
    }
}