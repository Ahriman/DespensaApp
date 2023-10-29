package com.marcossan.scanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.marcossan.scanner.ui.navigation.Screens

@Composable
fun MenuScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { navController.navigate(route = Screens.ScannerScreen.route) }) {
            Text(text = "Abrir el Scanner")
        }
        Button(onClick = { navController.navigate(route = Screens.MarsPhotosApp.route) }) {
            Text(text = "Abrir pantalla producto")
        }

    }

}