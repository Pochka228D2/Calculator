package com.example.zadanie1

import CalculatorMain
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.zadanie1.ui.theme.Blue
import com.example.zadanie1.ui.theme.Green
import com.example.zadanie1.ui.theme.ProductListScreen
import com.example.zadanie1.ui.theme.Screen
import com.example.zadanie1.ui.theme.Zadanie1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
                bottomBar = {
                    if (currentRoute != "menu") {
                        NavigationBar(containerColor = Color.White) {
                            val items = listOf(Screen.Calculator, Screen.CalcMain, Screen.Products)
                            items.forEach { screen ->
                                NavigationBarItem(
                                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                                    label = { Text(screen.title, fontSize = 12.sp) },
                                    selected = currentRoute == screen.route,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo("menu") { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                val padding = if (currentRoute == "menu") PaddingValues(0.dp) else innerPadding
                Box(modifier = Modifier.padding(padding)) {
                    NavHost(navController = navController, startDestination = "menu") {
                        composable("menu") { Gradient1(navController) }
                        composable("calculator") { Calculator(navController) }
                        composable("calculator_main") { CalculatorMain(navController) }
                        composable("product_list") { ProductListScreen(navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun Gradient1(navController: NavController) {
    val blushhh = Brush.verticalGradient(listOf(Green, Blue))
    val kek = FontFamily(Font(R.font.mmm))

    Box(modifier = Modifier.fillMaxSize().background(blushhh)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.lool),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(210.dp)
                    .padding(bottom = 20.dp)
                    .clickable {
                        navController.navigate("calculator") {
                            // Чтобы нельзя было вернуться назад на заставку
                            popUpTo("menu") { inclusive = true }
                        }
                    }
            )
            Text(
                text = "SIMPLE CALCULATOR",
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = kek
            )
        }
    }
}


