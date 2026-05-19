package com.example.zadanie1.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Menu : Screen("menu", "Меню", Icons.Default.Home)
    object Calculator : Screen("calculator", "Конвертер", Icons.Default.Transform)
    object CalcMain : Screen("calculator_main", "Калк", Icons.Default.Calculate)
    object Products : Screen("product_list", "Товары", Icons.Default.ShoppingCart)
}